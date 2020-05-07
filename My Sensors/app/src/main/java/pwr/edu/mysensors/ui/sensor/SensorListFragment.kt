package pwr.edu.mysensors.ui.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pwr.edu.mysensors.SensorAdapter
import pwr.edu.mysensors.databinding.SensorFragmentBinding

class SensorListFragment : Fragment() {
    private lateinit var viewModel: SensorListViewModel
    private lateinit var sensorManager: SensorManager
    private var _binding: SensorFragmentBinding? = null
    private lateinit var viewModelAdapter: SensorAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SensorFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SensorListViewModel::class.java)
        viewModelAdapter = SensorAdapter()

        // Require Sensor List
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        viewModel.transformSensorList(deviceSensors)

        // Look the list
        binding.sensorView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        viewModel.sensorList.observe(viewLifecycleOwner, Observer { list ->
            list?.apply {
                viewModelAdapter.submitList(list)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
