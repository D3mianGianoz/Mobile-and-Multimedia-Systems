package pwr.edu.mymlkit.image

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pwr.edu.mymlkit.R
import pwr.edu.mymlkit.databinding.PickImageFragmentBinding


class PickImageFragment : Fragment() {

    private lateinit var viewModel: PickImageViewModel
    private lateinit var binding: PickImageFragmentBinding

    /**
     * [pickPhotoRequestCode] is an integer which identify our callback in onActivityResult method,
     * suggest to use values above 100.
     */
    private val pickPhotoRequestCode = 777
    private val requestImageCapture = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.pick_image_fragment, container, false)
        binding.pickButton.setOnClickListener {
            pickImage()
        }
        binding.takeFotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PickImageViewModel::class.java)
        binding.viewModel = viewModel

        // Init the app
        processBitMap((binding.pickImageView.drawable as BitmapDrawable).bitmap)
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, pickPhotoRequestCode)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, requestImageCapture)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                pickPhotoRequestCode -> {
                    val bitmap = viewModel.getImageFromData(data, activity!!.contentResolver)
                    if (bitmap != null) {
                        processBitMap(bitmap)
                    }
                }
                requestImageCapture -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    processBitMap(imageBitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun processBitMap(imageBitmap: Bitmap) {
        imageBitmap.apply {
            binding.pickImageView.setImageBitmap(this)
            viewModel.processImageTagging(imageBitmap)
        }
    }
}
