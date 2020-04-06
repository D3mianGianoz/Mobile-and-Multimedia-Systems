package pwr.edu.mytaskmanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwr.edu.mytaskmanager.Task
import pwr.edu.mytaskmanager.TypeOfTask
import java.util.*

class MainViewModel : ViewModel() {
    private val _taskList = MutableLiveData<MutableList<Task>>()
    val taskList: LiveData<MutableList<Task>>
        get() = _taskList

    private val _navigateToSelectedTask = MutableLiveData<Task>()
    val navigateToSelectedTask: LiveData<Task>
        get() = _navigateToSelectedTask

    init {
        _taskList.value = mutableListOf(
            Task(
                "Importan VideoCall with Jon",
                "Please Damiano arrive 10 minutes early to complete your new patient paperwork." +
                        " We are located near the post office.",
                Date(),
                false,
                TypeOfTask.VIDEO_CALL
            ),
            Task(
                "Call the Dentist",
                "Hello, this is ___(name of your clinic)____ with an appointment reminder for (patient first name)" +
                        " Your appointment is scheduled on (day) (date) at (time).  Please remember to bring your updated insurance cards, photo ID," +
                        " an updated medication list and any co-pay or deductible.",
                Date(),
                false,
                TypeOfTask.PHONE
            ),
            Task(
                "How to Make a Reminder Works for You",
                "Computers and ubiquity of mobile Internet-connected devices" +
                        " make it possible to set up automatic triggers for just about anything.\n" +
                        "\n" +
                        "Desktop software like Outlook will pop up reminders on your desktop screen, and most online services go an extra step " +
                        "and send reminders via email or SMS text message — just the thing to keep you on track. Sandy, for example, just does automatic reminders.",
                Date(),
                false,
                TypeOfTask.TODO
            ),
            Task(
                "Meeting on PASS Process",
                "Hi Jim, I just wanted to remind you about the meeting we have scheduled for Monday October 5 at 10:00am. It's being held" +
                        " in conference room A, and we'll be discussing the new PASS Process. If you have any questions, feel free to get in touch (x3024). Best wishes,",
                Date(),
                false,
                TypeOfTask.MEETING
            ),
            Task(
                "Write to Dennis", "Hope you are well.\n" +
                        "\n" +
                        "I'm writing to you, yet again, in your capacity as \"Answer Man.\"\n" +
                        "\n" +
                        "One of our David English House teachers has just e-mailed me to see if I\n" +
                        "have any more information on \"university listening tests\" which are to be\n" +
                        "administered soon.\n" +
                        "\n" +
                        "I have no information about any such tests.　 Do you?　 If so, could you\n" +
                        "please let me know.\n" +
                        "\n" +
                        "Thank you kindly.", Date(), false, TypeOfTask.EMAIL
            )
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

    /**
     * Add a task to the list safely
     */
    fun addTask(task: Task) {
        _taskList.value?.add(task)
    }

    /**
     * Remove a task to the list safely
     */
    fun removeTask(task: Task) {
        _taskList.value?.remove(task)
    }

    /**
     * Remove a task to the list safely
     */
    fun removeTaskWithPosition(position: Int) {
        _taskList.value?.removeAt(position)
    }

    /**
     * Mark as Done a task of the list safely
     */
    fun markAsDoneTask(task: Task) {
        _taskList.value?.forEach { t: Task? -> if (t?.equals(task)!!) t.status = true }
    }

    /**
     * Mark as Done a task of the list safely
     */
    fun markAsDoneTaskWithPosition(position: Int) {
        _taskList.value?.get(position)?.status = true
    }
}
