package pwr.edu.myinfo.bmi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwr.edu.myinfo.R
import pwr.edu.myinfo.data.BmiCounter
import pwr.edu.myinfo.data.BmiFormState

class BmiViewModel : ViewModel() {

    val weight: MutableLiveData<String> = MutableLiveData()
    val height: MutableLiveData<String> = MutableLiveData()
    val imperial: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _result = MutableLiveData<String>("Test")
    val result: LiveData<String>
        get() = _result

    private val _eventCalculate = MutableLiveData<Boolean>()
    val eventCalculate: LiveData<Boolean>
        get() = _eventCalculate

    private val _eventNavigate = MutableLiveData<Boolean>()
    val eventNavigate: LiveData<Boolean>
        get() = _eventNavigate

    private val _bmiFormState = MutableLiveData<BmiFormState>()
    val bmiFormState: LiveData<BmiFormState>
        get() = _bmiFormState

    fun bmiDataChanged() {
        when {
            weight.value == null || weight.value!!.isEmpty() ->
                return
            height.value == null || height.value!!.isEmpty() ->
                return
            !isWeightValid(weight.value!!.toDouble()) ->
                _bmiFormState.value =
                    BmiFormState(
                        massError = R.string.bmiF_mass_error
                    )
            !isHeightValid(height.value!!.toDouble()) ->
                _bmiFormState.value =
                    BmiFormState(
                        heightError = R.string.bmiF_height_error
                    )
            else -> _bmiFormState.value =
                BmiFormState(isDataValid = true)
        }
    }

    private fun isHeightValid(height: Double): Boolean {
        return if (imperial.value!!) {
            height > 10 && height < 100
        } else { //metric system
            height > 30 && height < 254
        }
    }

    private fun isWeightValid(weight: Double): Boolean {
        return if (imperial.value!!) {
            weight > 5 && weight < 1543
        } else { //metric system
            weight > 2 && weight < 700
        }
    }

    fun onCalculateBmi() {
        val mass: Double = weight.value!!.toDouble()
        val height: Double = height.value!!.toDouble()

        val bmi = BmiCounter(height = height, mass = mass)

        if (imperial.value!!) {
            _result.value = bmi.bmiImperial()
        } else {
            _result.value = bmi.bmiMetric()
        }

        _eventCalculate.value = true
    }

    fun onBmiDetails() {
        _eventNavigate.value = true
    }

    fun onNavigatedAway() {
        _eventNavigate.value = false
    }
}
