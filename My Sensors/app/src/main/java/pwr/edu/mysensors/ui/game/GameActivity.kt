package pwr.edu.mysensors.ui.game

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import pwr.edu.mysensors.R

class BalanceGame : AppCompatActivity(), SensorEventListener {

    internal lateinit var ball: Bitmap
    private lateinit var sensorManager: SensorManager

    /**
     * Coordinates and other physics data necessary to track the ball
     */
    private var xPos = 0.0f; private var xAcc = 0.0f; private var xVel = 0.0f
    private var yPos = 0.0f; private var yAcc = 0.0f; private var yVel = 0.0f
    private var xMax = 0.0f; private var yMax = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(GraficaBall(this))

        val size = Point()
        val screenDisplay = windowManager.defaultDisplay
        screenDisplay.getSize(size)
        xMax = size.x.toFloat() - 100
        yMax = size.y.toFloat() - 220
        xPos = size.x.toFloat() / 2
        yPos = size.y.toFloat() / 2

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onStop() {
        sensorManager.unregisterListener(this)
        super.onStop()
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            xAcc = sensorEvent.values[0]
            yAcc = -sensorEvent.values[1]
            updateBallPosition()
        }
    }

    private fun updateBallPosition() {
        val refreshRate = 0.6f
        xVel += xAcc * refreshRate
        yVel += yAcc * refreshRate
        xPos -= xVel / 2 * refreshRate
        yPos -= yVel / 2 * refreshRate
        if (xPos > xMax || yPos > yMax || xPos < 0 || yPos < 0) gameOver()
    }

    private fun gameOver() {
        sensorManager.unregisterListener(this)
        AlertDialog.Builder(this)
            .setTitle("Game over!")
            .setMessage("Do you want to play again?")
            .setPositiveButton(android.R.string.yes) { _, _ ->
                this.recreate()
            }
            .setNegativeButton(android.R.string.no) { _, _ ->
                NavUtils.navigateUpFromSameTask(this)
            }
            .show()
    }

    override fun onAccuracyChanged(sensor: Sensor?, i: Int) {
        // Not modify
    }

    inner class GraficaBall(context: Context?) : View(context) {
        override fun onDraw(canvas: Canvas) {
            canvas.drawBitmap(ball, xPos, yPos, null)
            invalidate()
        }

        init {
            val ballSrc = BitmapFactory.decodeResource(resources, R.drawable.ball_8)
            ball = Bitmap.createScaledBitmap(ballSrc, 100, 100, true)
        }
    }
}