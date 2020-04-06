package pwr.edu.mytaskmanager.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import pwr.edu.mytaskmanager.R
import pwr.edu.mytaskmanager.databinding.DetailsFragmentBinding
import pwr.edu.mytaskmanager.ui.ViewModelFactory
import pwr.edu.mytaskmanager.ui.main.MainViewModel

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Retrive the task
        val args: DetailsFragmentArgs by navArgs()
        viewModel =
            ViewModelProvider(this, ViewModelFactory(args.task)).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
        binding.viewModel = viewModel

        val mainViewModel = activity?.run {
            ViewModelProvider(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        viewModel.deleteTaskEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.falseRemoveTask()
                mainViewModel.removeTask(task = args.task)
//                Snackbar.make(view!!, "Task Removed", Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })

        viewModel.markDoneEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.falseMarkDoneTask()
                mainViewModel.markAsDoneTask(task = args.task)
//                Snackbar.make(view!!, "Task Completed", Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
    }

}
