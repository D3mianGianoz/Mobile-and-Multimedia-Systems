package pwr.edu.mytaskmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pwr.edu.mytaskmanager.Task
import pwr.edu.mytaskmanager.ui.details.DetailsViewModel

class ViewModelFactory(private val task: Task) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(task) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}