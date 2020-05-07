package pwr.edu.mysensors.ui.sensor

import android.hardware.Sensor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwr.edu.mysensors.SensorPretty

class SensorListViewModel : ViewModel() {
    private val _sensorList: MutableLiveData<List<SensorPretty>> = MutableLiveData()
    val sensorList: LiveData<List<SensorPretty>>
        get() = _sensorList

    fun transformSensorList(sensors: List<Sensor>) {
        _sensorList.value = sensors.map {
            SensorPretty(
                "Nome ${it.name}",
                "Type ${it.stringType}",
                "Vendor ${it.vendor}",
                "Version ${it.version}"
            )
        }
    }
}
