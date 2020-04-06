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

    private val _type = MutableLiveData(TypeOfTask.EMAIL)
    val type: LiveData<TypeOfTask>
        get() = _type

    private val _pickDateEvent = MutableLiveData<Boolean>()
    val pickDateEvent: LiveData<Boolean>
        get() = _pickDateEvent

    private val _pickTypeEvent = MutableLiveData<Boolean>()
    val pickTypeEvent: LiveData<Boolean>
        get() = _pickTypeEvent

    private val _cancelCreateEvent = MutableLiveData<Boolean>()
    val cancelCreateEvent: LiveData<Boolean>
        get() = _cancelCreateEvent

    private val _createTaskEvent = MutableLiveData<Boolean>()
    val createTaskEvent: LiveData<Boolean>
        get() = _createTaskEvent

    fun storeTheTask() {
        _task.value = Task(
            title = title.value,
            description = description.value,
            dueTueDate = date.value,
            typeOfTask = _type.value!!,
            status = false
        )
        onStoreEvent()
    }

    fun updateType(taskString: String) {
        _type.value = when (taskString) {
            "Todo" -> TypeOfTask.TODO
            "Email" -> TypeOfTask.EMAIL
            "Phone" -> TypeOfTask.PHONE
            "Meeting" -> TypeOfTask.MEETING
            "Video Call" -> TypeOfTask.VIDEO_CALL
            else -> TypeOfTask.EMAIL
        }
    }

    fun falseDateEvent() {
        _pickDateEvent.value = false
    }

    fun onDateEvent() {
        _pickDateEvent.value = true
    }

    fun onTypeEvent() {
        _pickTypeEvent.value = true
    }

    fun falseTypeEvent() {
        _pickTypeEvent.value = false
    }

    fun onCancelTask() {
        _cancelCreateEvent.value = true
    }

    fun falseCancelEvent() {
        _cancelCreateEvent.value = false
    }

    private fun onStoreEvent() {
        _createTaskEvent.value = true
    }

    fun falseStoreEvent() {
        _createTaskEvent.value = false
    }
}
