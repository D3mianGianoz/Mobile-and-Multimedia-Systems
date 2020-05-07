package pwr.edu.mysensors.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pwr.edu.mysensors.R
import pwr.edu.mysensors.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = MainFragmentBinding.inflate(layoutInflater, container, false)

        binding.gameBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_ballGame)
        }

        binding.gpsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_gpsAreaActivity)
        }

        binding.sensorBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
