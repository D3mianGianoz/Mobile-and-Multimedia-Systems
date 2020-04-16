package pwr.edu.mymlkit.text

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.text_recognition_fragment.*
import pwr.edu.mymlkit.R
import pwr.edu.mymlkit.databinding.TextRecognitionFragmentBinding


class TextRecognitionFragment : Fragment() {

    private lateinit var viewModel: TextRecognitionViewModel
    private lateinit var binding: TextRecognitionFragmentBinding

    private val TAG = "OcrCaptureActivity"

    // Intent request code to handle updating play services if needed.
    private val RC_HANDLE_GMS = 9001

    // Permission request codes need to be < 256
    private val RC_HANDLE_CAMERA_PERM = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.text_recognition_fragment, container, false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recognizeTextBt.setOnClickListener {
            startRecognizing(it)
        }
        binding.selectImageBt.setOnClickListener {
            selectImage()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TextRecognitionViewModel::class.java)
        binding.viewModel = viewModel
        // TODO: Use the ViewModel
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RC_HANDLE_GMS)
    }

    private fun startRecognizing(v: View) {
        if (imageView.drawable != null) {
            viewModel.recognizeText(imageView = binding.imageView, view = v)
        } else {
            Toast.makeText(context, "Select an Image First", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_HANDLE_GMS && resultCode == Activity.RESULT_OK) {
            binding.imageView.setImageURI(data!!.data)
        }
    }

}
