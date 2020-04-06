package pwr.edu.mytaskmanager

import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
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
    val title: String?,
    val description: String?,
    val dueTueDate: Date?,
    var status: Boolean,
    val typeOfTask: TypeOfTask
) : Parcelable

@BindingAdapter("typeOfTask")
fun bindType(imageView: ImageView, task: TypeOfTask) {
    when (task) {
        TypeOfTask.TODO -> imageView.setImageResource(R.drawable.ic_check_circle_black_24dp)
        TypeOfTask.EMAIL -> imageView.setImageResource(R.drawable.ic_email_black_24dp)
        TypeOfTask.PHONE -> imageView.setImageResource(R.drawable.ic_phone_black_24dp)
        TypeOfTask.MEETING -> imageView.setImageResource(R.drawable.ic_people_black_24dp)
        TypeOfTask.VIDEO_CALL -> imageView.setImageResource(R.drawable.ic_video_call_black_24dp)
    }
}

@BindingAdapter("statusOfTask")
fun bindStatus(imageView: ImageView, boolean: Boolean) {
    when (boolean) {
        true -> imageView.apply { setImageResource(R.drawable.ic_done_black_24dp) }
        false -> imageView.apply { setImageResource(R.drawable.ic_close_black_24dp) }
    }
}

@BindingAdapter("statusOfButton")
fun bindButtonToStatus(button: Button, boolean: Boolean) {
    when (boolean) {
        true -> button.isEnabled = false
        false -> button.isEnabled = true
    }
}


fun EditText.validate(message: String, validator: (String) -> Boolean) {
    this.doAfterTextChanged {
        this.error = if (validator(this.text.toString())) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}
