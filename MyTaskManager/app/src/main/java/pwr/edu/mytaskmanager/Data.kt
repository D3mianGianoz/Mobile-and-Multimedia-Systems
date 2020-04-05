package pwr.edu.mytaskmanager

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import kotlinx.android.parcel.Parcelize
import java.util.*

enum class TypeOfTask {
    TODO,
    EMAIL,
    PHONE,
    MEETING,
    VIDEO_CALL
}

@Parcelize
data class Task(
    val title: String,
    val description: String,
    val dueTueDate: Date,
    val status: Boolean,
    val typeOfTask: TypeOfTask
) : Parcelable

@BindingAdapter("typeOfTask")
fun bindStatus(imageView: ImageView, task: TypeOfTask) {
    when (task) {
        TypeOfTask.TODO -> imageView.setImageResource(R.drawable.ic_check_circle_black_24dp)
        TypeOfTask.EMAIL -> imageView.setImageResource(R.drawable.ic_email_black_24dp)
        TypeOfTask.PHONE -> imageView.setImageResource(R.drawable.ic_phone_black_24dp)
        TypeOfTask.MEETING -> imageView.setImageResource(R.drawable.ic_people_black_24dp)
        TypeOfTask.VIDEO_CALL -> imageView.setImageResource(R.drawable.ic_video_call_black_24dp)
    }
}