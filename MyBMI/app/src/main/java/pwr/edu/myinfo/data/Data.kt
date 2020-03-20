package pwr.edu.myinfo.data

/**
 * Data validation state of the bmi form.
 */
data class BmiFormState(
    val heightError: Int? = null,
    val massError: Int? = null,
    val isDataValid: Boolean = false
)

/**
 * Simple class for calculating bm
 */
class BmiCounter(private val height: Double, private val mass: Double){
    fun bmiMetric(): String {
        // Conversion cm in m
        val heightInM = height.div(100)
        return (mass / (heightInM * heightInM)).toString().take(6)
    }
    fun bmiImperial(): String {
        return mass.div(height.times(height)).times(703).toString().take(6)
    }
}
