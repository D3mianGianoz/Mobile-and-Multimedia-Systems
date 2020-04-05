package pwr.edu.mytaskmanager.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import pwr.edu.mytaskmanager.R
import pwr.edu.mytaskmanager.Task
import pwr.edu.mytaskmanager.databinding.DetailsFragmentBinding
import pwr.edu.mytaskmanager.ui.ViewModelFactory

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
        viewModel = ViewModelProvider(this, ViewModelFactory(args.task)).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
        binding.viewModel = viewModel
    }

}
