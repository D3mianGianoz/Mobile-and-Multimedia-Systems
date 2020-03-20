package pwr.edu.myinfo.bmi

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import pwr.edu.myinfo.R
import pwr.edu.myinfo.databinding.FragmentBmiBinding


/**
 * A simple [Fragment] subclass.
 * Use the [BmiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BmiFragment : Fragment() {

    private lateinit var viewModel: BmiViewModel
    private lateinit var massInput: TextInputLayout
    private lateinit var heightInput: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding: FragmentBmiBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_bmi, container, false
        )

        // set the option menu
        setHasOptionsMenu(true);

        // Init the viewModel
        viewModel = ViewModelProvider(this).get(BmiViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val registration = binding.bmiFCalcuateBt
        val height = binding.bmiFHeightEditText
        val mass = binding.bmiFMassEditText

        heightInput = binding.bmiFHeightInput
        massInput = binding.bmiFMassInput

        height.doAfterTextChanged { viewModel.bmiDataChanged() }
        mass.doAfterTextChanged { viewModel.bmiDataChanged() }

        viewModel.bmiFormState.observe(viewLifecycleOwner, Observer {
            val formState = it ?: return@Observer

            registration.isEnabled = formState.isDataValid
            height.error = formState.heightError?.let { it1 -> getString(it1) }
            mass.error = formState.massError?.let { it2 -> getString(it2) }
        })

        viewModel.eventNavigate.observe(viewLifecycleOwner, Observer {
            if (it) {
                val direction = BmiFragmentDirections.actionBmiFragmentToDetailsFragment(viewModel.result.value!!.toFloat())
                findNavController().navigate(direction)
                viewModel.onNavigatedAway()
            }
        })

        viewModel.eventCalculate.observe(viewLifecycleOwner, Observer {
            if (it){
                val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("Debug", "Funziona $item")

        // Handle item selection
        return when (item.itemId) {
            R.id.action_imperial -> {
                updateUiImperial()
                true
            }
            R.id.action_metric -> {
                updateUiMetric()
                true
            }
            R.id.action_about -> {
                findNavController().navigate(R.id.action_bmiFragment_to_profile)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUiMetric() {
        heightInput.apply {
            hint = getString(R.string.bmiF_hint_cm)
        }
        massInput.apply {
            hint = getString(R.string.bmiF_hint_kg)
        }
        viewModel.imperial.value = false
    }

    private fun updateUiImperial() {
        heightInput.apply {
            hint = getString(R.string.bmiF_hint_inches)
        }
        massInput.apply {
            hint = getString(R.string.bmiF_hint_pounds)
        }
        viewModel.imperial.value = true
    }
}
