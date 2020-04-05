package pwr.edu.mytaskmanager.ui.create

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pwr.edu.mytaskmanager.R
import pwr.edu.mytaskmanager.databinding.CreateTaskFragmentBinding
import java.util.*

class CreateTaskFragment : Fragment() {

    private lateinit var viewModel: CreateTaskViewModel
    private lateinit var binding: CreateTaskFragmentBinding
    private lateinit var typeOfTask: Array<out String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_task_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        typeOfTask = resources.getStringArray(R.array.type_of_task)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.pickDateEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                onDateSelected()
                viewModel.falseDateEvent()
            }
        })

        viewModel.pickTypeEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                onTypeSelected()
                viewModel.falseTyepEvent()
            }
        })
        // TODO: Use the ViewModel
    }

    private fun onTypeSelected() {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle("Type of Task")
            .setSingleChoiceItems(typeOfTask, 1) { _, which -> viewModel.updateType(typeOfTask[which]) }
        builder.show()
    }


    private fun onDateSelected() {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText(R.string.date_hint)
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            val test = Date(it)
            Log.w("Test", "Ora ora $test, il long vale $it")
            viewModel.date.value = test
        }
        picker.show(childFragmentManager, picker.toString())
        Log.wtf("Debug test", "La selezione del datePicker è: $picker.selection")
    }

}
