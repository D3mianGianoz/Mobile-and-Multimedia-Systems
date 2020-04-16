package pwr.edu.mymlkit.text

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText

class TextRecognitionViewModel : ViewModel() {
    private val _tagsText = MutableLiveData<String>()
    val tagsText: LiveData<String>
        get() = _tagsText

    // TODO: Implement the ViewModel
    fun recognizeText(imageView: ImageView, view: View) {
        _tagsText.value = ""
        view.isEnabled = false
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                view.isEnabled = true
                processResultText(firebaseVisionText)
            }
            .addOnFailureListener {
                view.isEnabled = true
                _tagsText.value = "False"
            }
    }

    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == 0) {
            _tagsText.value = ("No Text Found")
            return
        }
        for (block in resultText.textBlocks) {
            val blockText = block.text
            Log.w("CIAO", blockText)
            _tagsText.value += (blockText + "\n")
        }
    }
}
