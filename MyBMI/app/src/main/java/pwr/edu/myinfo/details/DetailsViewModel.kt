package pwr.edu.myinfo.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {

    val result = MutableLiveData<String>("")
}
