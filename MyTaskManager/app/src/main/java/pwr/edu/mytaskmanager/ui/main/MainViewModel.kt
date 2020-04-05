package pwr.edu.mytaskmanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwr.edu.mytaskmanager.Task
import pwr.edu.mytaskmanager.TypeOfTask
import java.util.*

class MainViewModel : ViewModel() {
    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>>
        get() = _taskList

    private val _navigateToSelectedTask = MutableLiveData<Task>()
    val navigateToSelectedTask: LiveData<Task>
        get() = _navigateToSelectedTask

    init {
        _taskList.value = listOf(
            Task("Cura", "Curarsi dalle malattie del cuore", Date(), false, TypeOfTask.VIDEO_CALL),
            Task("Chiama mamma", "Chiamare mamma", Date(), false, TypeOfTask.PHONE)
        )
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedTask] [MutableLiveData]
     * @param task The [Task] that was clicked on.
     */
    fun displayTaskDetails(task: Task) {
        _navigateToSelectedTask.value = task
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayTaskDetailsComplete() {
        _navigateToSelectedTask.value = null
    }
}
