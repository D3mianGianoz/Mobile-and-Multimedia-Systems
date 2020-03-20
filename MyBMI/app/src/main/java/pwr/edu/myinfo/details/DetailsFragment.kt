package pwr.edu.myinfo.details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.rangeTo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import pwr.edu.myinfo.R
import pwr.edu.myinfo.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_details, container, false
        )
        // For safety
        setHasOptionsMenu(false)

        // Init the viewModel
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val colorArray: Array<Int> = arrayOf(R.color.yellow_anoressia,
            R.color.green_salute, R.color.orange_overweight, R.color.red_obesity,
            R.color.dark_red_sever_obesity, R.color.very_dark_red_sever_obesity)

        val statusBmiArray: Array<String> = resources.getStringArray(R.array.bmi_status)

        // Logic for each description
        val sharedIndex: Int = when (args.bmiValue) {
            in 0f.rangeTo(18.5f) ->  0
            in 18.51f.rangeTo(24.9f) -> 1
            in 25f.rangeTo(29.9f) -> 2
            in 30f.rangeTo(34.59f) -> 3
            in 35f.rangeTo(39.9f) -> 4
            else -> 5
        }
        // Description and colors
        viewModel.result.value = statusBmiArray[sharedIndex]
        binding.detailsFDescription.setTextColor(resources.getColor(colorArray[sharedIndex]))

        return binding.root
    }


}
