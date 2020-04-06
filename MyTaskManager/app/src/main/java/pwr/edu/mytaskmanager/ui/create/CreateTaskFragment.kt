package pwr.edu.mytaskmanager.ui.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pwr.edu.mytaskmanager.R
import pwr.edu.mytaskmanager.databinding.CreateTaskFragmentBinding
import pwr.edu.mytaskmanager.ui.main.MainViewModel
import pwr.edu.mytaskmanager.validate
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
        binding.descriptionInput.validate("Minimum length is 6") { s -> s.length >= 6 }
        binding.titleInput.validate("Minimum length is 6") { s -> s.length >= 6 }
        typeOfTask = resources.getStringArray(R.array.type_of_task)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)
        binding.viewModel = viewModel

        val mainViewModel = activity?.run {
            ViewModelProvider(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        // Choose Date
        viewModel.pickDateEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                onDateSelected()
                viewModel.falseDateEvent()
            }
        })

        // Choose Type
        viewModel.pickTypeEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                onTypeSelected()
                viewModel.falseTypeEvent()
            }
        })

        // Cancel the operation
        viewModel.cancelCreateEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.falseCancelEvent()
                findNavController().navigateUp()
            }
        })

        viewModel.createTaskEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.falseStoreEvent()
                Log.wtf("Strange", "Allora ${viewModel.createTaskEvent}")
                mainViewModel.addTask(viewModel.task.value!!)
                findNavController().navigateUp()
            }
        })
    }

    private fun onTypeSelected() {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle("Type of Task")
            .setSingleChoiceItems(
                typeOfTask,
                1
            ) { _, which -> viewModel.updateType(typeOfTask[which]) }
            .setPositiveButton("Ok", null)
        builder.show()
    }


    private fun onDateSelected() {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText(R.string.date_hint)
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            val test = Date(it)
            viewModel.date.value = test
        }
        picker.show(childFragmentManager, picker.toString())
    }

}
