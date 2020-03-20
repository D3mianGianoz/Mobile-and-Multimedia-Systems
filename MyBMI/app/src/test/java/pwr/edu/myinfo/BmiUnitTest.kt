package pwr.edu.myinfo

import org.junit.Test

import org.junit.Assert.*
import pwr.edu.myinfo.data.BmiCounter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BmiUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun bmi_metric_healthy_isCorrect() {
        val bmiTest = BmiCounter(187.0, 82.5)
        assertEquals("23.592", bmiTest.bmiMetric())
    }
    @Test
    fun bmi_imperial_healthy_isCorrect() {
        val bmiTest = BmiCounter(100.0, 300.0)
        assertEquals("21.09", bmiTest.bmiImperial())
    }
    @Test
    fun bmi_metric_obese_isCorrect() {
        val bmiTest = BmiCounter(150.3, 77.5)
        assertEquals("34.307", bmiTest.bmiMetric())
    }
    @Test
    fun bmi_imperial_overweight_isCorrect() {
        val bmiTest = BmiCounter(50.0, 100.0)
        assertEquals("28.12", bmiTest.bmiImperial())
    }
    @Test
    fun bmi_metric_underweight_isCorrect() {
        val bmiTest = BmiCounter(170.0, 47.5)
        assertEquals("16.435", bmiTest.bmiMetric())
    }
    @Test
    fun bmi_imperial_severlyObese_isCorrect() {
        val bmiTest = BmiCounter(50.0, 150.0)
        assertEquals("42.18", bmiTest.bmiImperial())
    }
}
