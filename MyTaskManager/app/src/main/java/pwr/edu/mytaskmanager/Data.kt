package pwr.edu.mytaskmanager

import java.util.*

enum class TypeOfTask {
    TODO,
    EMAIL,
    PHONE,
    MEETING,
    VIDEOCALL
}

data class Task(val title: String,
           val description: String,
           val dueTueDate: Date,
           val status: Boolean,
           val typeOfTask: TypeOfTask)