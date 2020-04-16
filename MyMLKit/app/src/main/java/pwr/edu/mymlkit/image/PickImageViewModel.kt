package pwr.edu.mymlkit.image

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class PickImageViewModel : ViewModel() {

    private val _tagsText = MutableLiveData("")
    val tagsText: LiveData<String>
        get() = _tagsText

    fun getImageFromData(data: Intent?, contentResolver: ContentResolver): Bitmap? {
        val selectedImage = data?.data
        return MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
    }

    /**
     * FirebaseVision introduce two methods to labeling (tagging) photos:
     * - getOnDeviceImageLabeler() process image directly on device using ready machine learning solutions from TensorFlow Lite.
     * - getCloudImageLabeler() process image in the cloud (required additional configuration)
     */
    fun processImageTagging(bitmap: Bitmap) {
        val visionImg = FirebaseVisionImage.fromBitmap(bitmap)
        FirebaseVision.getInstance().onDeviceImageLabeler.processImage(visionImg)
            .addOnSuccessListener { tags ->
                _tagsText.postValue(tags.joinToString(" ") { it.text })
                Log.w("LAB", _tagsText.value!!)
            }
            .addOnFailureListener { ex -> Log.wtf("LAB", ex) }
    }
}
