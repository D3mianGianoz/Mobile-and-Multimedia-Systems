package pwr.edu.mymlkit.`object`

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.util.Size
import androidx.camera.core.ImageProxy
import androidx.core.graphics.toRectF
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.objects.FirebaseVisionObject
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions
import de.crysxd.cameraXTracker.ThreadedImageAnalyzer
import de.crysxd.cameraXTracker.ar.ArObject
import de.crysxd.cameraXTracker.ar.ArObjectTracker
import java.util.concurrent.atomic.AtomicBoolean


class MyImageAnalyzer : ViewModel(), ThreadedImageAnalyzer {

    val arObjectTracker = ArObjectTracker()
    private val isBusy = AtomicBoolean(false)
    private val uiHandler = Handler(Looper.getMainLooper())
    private val handlerThread = HandlerThread("MyImageAnalyzer").apply { start() }
    private val objectDetector = FirebaseVision.getInstance().getOnDeviceObjectDetector(
        FirebaseVisionObjectDetectorOptions.Builder()
            .setDetectorMode(FirebaseVisionObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .build()
    )

    override fun getHandler() = Handler(handlerThread.looper)

    override fun analyze(image: ImageProxy, rotationDegrees: Int) {
        if (image.image != null && isBusy.compareAndSet(false, true)) {
            // Create an FirebaseVisionImage. The image's bytes are in YUV_420_888 format (camera2 API)
            // If you use camera (deprecated, pre L) instead of camera2, use:
            // FirebaseVisionImage.fromByteArray(data, FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            // and pass the bytes from the Camera.PreviewCallback. Pay attention to rotation of the image in this case!
            val rotation = rotationDegreesToFirebaseRotation(rotationDegrees)
            val visionImage = FirebaseVisionImage.fromMediaImage(image.image!!, rotation)
            val imageSize = Size(image.width, image.height)
            objectDetector.processImage(visionImage).addOnCompleteListener {
                isBusy.set(false)

                // Error? Log it :(
                if (it.exception != null) {
                    Log.e("Error", it.exception.toString())
                    return@addOnCompleteListener
                }


                // Get the first object of CATEGORY_FASHION_GOOD (first = most prominent) or the already tracked object
                val visionObject = it.result?.firstOrNull { o ->
                    o.classificationCategory == FirebaseVisionObject.CATEGORY_FASHION_GOOD && o.trackingId != null
                }
                // Hand the object to the tracker. It will interpolate the path and ensure a fluent visual even if we dropped
                // frames because the detection was too slow
                uiHandler.post {
                    arObjectTracker.processObject(
                        if (visionObject != null) {
                            ArObject(
                                trackingId = visionObject.trackingId
                                    ?: -1 /* An ID of the tracked object, e.g. from Firebase ML Kit*/,
                                boundingBox = visionObject.boundingBox.toRectF() /* The bounding box */,
                                sourceSize = imageSize /* See above, the size of the input image */,
                                sourceRotationDegrees = rotationDegrees /* See above, the roation of the input image */
                            )
                        } else {
                            null
                        }
                    )
                }
            }
        }
    }

    private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int) =
        when (rotationDegrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw IllegalArgumentException("Rotation $rotationDegrees not supported")
        }
}
