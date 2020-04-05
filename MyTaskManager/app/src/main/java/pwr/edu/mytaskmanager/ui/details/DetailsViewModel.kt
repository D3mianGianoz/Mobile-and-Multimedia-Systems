package pwr.edu.mytaskmanager.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwr.edu.mytaskmanager.Task

class DetailsViewModel(outsideTask: Task) : ViewModel() {
    private val _task = MutableLiveData(outsideTask)
    val task: LiveData<Task>
        get() = _task

    fun onRemoveTask() {
        // TODO
    }
}
