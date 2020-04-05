package pwr.edu.mytaskmanager.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pwr.edu.mytaskmanager.R
import pwr.edu.mytaskmanager.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelAdapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        //set the option menu
        setHasOptionsMenu(true);

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModelAdapter = TasksAdapter(TasksAdapter.OnClickListener {
            viewModel.displayTaskDetails(it)
        })

        /**
         * Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
         * After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
         * for another navigation event.
         */
        viewModel.navigateToSelectedTask.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // passing the task data inside safeArgs
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(it)
                this.findNavController().navigate(action)

                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayTaskDetailsComplete()
            }
        })


        binding.taskRecycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        viewModel.taskList.observe(viewLifecycleOwner, Observer { list ->
            list?.apply {
                viewModelAdapter.submitList(list)
            }
        })

        binding.fabCreateTask.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createTaskFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("Debug", "Funziona $item")
        // Handle item selection
        return when (item.itemId) {
            R.id.action_about -> {
                findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
