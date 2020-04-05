package pwr.edu.mytaskmanager.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwr.edu.mytaskmanager.Task
import pwr.edu.mytaskmanager.TypeOfTask
import java.util.*


class CreateTaskViewModel : ViewModel() {
    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task>
        get() = _task

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val date = MutableLiveData<Date>()
//    val date: LiveData<Date>
//        get() = _date

    private val _type = MutableLiveData<TypeOfTask>(TypeOfTask.EMAIL)
    val type: LiveData<TypeOfTask>
        get() = _type

    private val _pickDateEvent = MutableLiveData<Boolean>()
    val pickDateEvent: LiveData<Boolean>
        get() = _pickDateEvent

    private val _pickTypeEvent = MutableLiveData<Boolean>()
    val pickTypeEvent: LiveData<Boolean>
        get() = _pickTypeEvent

    fun falseDateEvent() {
        _pickDateEvent.value = false
    }

    fun onDateEvent() {
        _pickDateEvent.value = true
    }

    fun onTypeEvent() {
        _pickTypeEvent.value = true
    }

    fun falseTyepEvent() {
        _pickTypeEvent.value = false
    }

    fun onStoreTheTask() {
        // TODO
    }

    fun updateType(s: String) {
        _type.value = TypeOfTask.valueOf(s)
    }
}
