package pwr.edu.mytaskmanager.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwr.edu.mytaskmanager.Task

class DetailsViewModel(outsideTask: Task) : ViewModel() {
    private val _task = MutableLiveData(outsideTask)
    val task: LiveData<Task>
        get() = _task

    private val _deleteTaskEvent = MutableLiveData<Boolean>()
    val deleteTaskEvent: LiveData<Boolean>
        get() = _deleteTaskEvent

    private val _markDoneEvent = MutableLiveData<Boolean>()
    val markDoneEvent: LiveData<Boolean>
        get() = _markDoneEvent

    fun onRemoveTask() {
        _deleteTaskEvent.value = true
    }

    fun falseRemoveTask() {
        _deleteTaskEvent.value = false
    }

    fun onMarkDoneTask() {
        _markDoneEvent.value = true
    }

    fun falseMarkDoneTask() {
        _markDoneEvent.value = false
    }
}
