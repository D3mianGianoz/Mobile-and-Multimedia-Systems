package pwr.edu.mysensors.ui.gps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import pwr.edu.mysensors.R
import pwr.edu.mysensors.databinding.ActivityGpsAreaBinding

class GpsAreaActivity : AppCompatActivity() {

    companion object {
        const val LATITUDINE_DOM = 51.1031308
        const val LONGITUDINE_DOM = 17.0845671
        const val REQUEST_PERMISSION_LOCATION = 2342
        const val PROXIMITY = 0.0020000
    }

    private lateinit var activityBinding: ActivityGpsAreaBinding

    // SensorObj
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location

    private var outsideArea: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        activityBinding = ActivityGpsAreaBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Permission necessary for the core function of the activity
            buildAlertMessageNoGps()
        }

        // Init location request
        mLocationRequest = LocationRequest()

    }

    private fun buildAlertMessageNoGps() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.enableGpsQ))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.y)) { _, _ ->
                startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 1233
                )
            }
            .setNegativeButton(getString(R.string.n)) { dialog, _ ->
                dialog.cancel()
                finish()
            }
            .create()
            .show()
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
            Toast.makeText(this, getString(R.string.listening), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        stopGpsUpdates()
        activityBinding.monitor.text = getString(R.string.stopped)
        Toast.makeText(this, getString(R.string.stoppedListening), Toast.LENGTH_SHORT).show()
    }

    private fun startLocationUpdates() {
        mLocationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 2000
            fastestInterval = 1000
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location) {
        mLastLocation = location
        activityBinding.latitudine.text = "Latitude : ${mLastLocation.latitude}"
        activityBinding.longitudine.text = "Longitude : ${mLastLocation.longitude}"
        checkIfOutOfArea()
    }

    private fun checkIfOutOfArea() {
        var message: String = "Listening"
        if (mLastLocation.latitude < LATITUDINE_DOM - PROXIMITY || mLastLocation.latitude > LATITUDINE_DOM + PROXIMITY
            || mLastLocation.longitude < LONGITUDINE_DOM - PROXIMITY || mLastLocation.longitude > LONGITUDINE_DOM + PROXIMITY) {
            if (!outsideArea) {
                message = getString(R.string.leftNeigh)
                outsideArea = true
                activityBinding.monitor.apply {
                    text = getString(R.string.outOfNeigh)
                    setTextColor(Color.RED)
                }
            }
        } else {
            if (outsideArea) {
                message = getString(R.string.backInNeigh)
                outsideArea = false
                activityBinding.monitor.apply {
                    text = getString(R.string.inNeigh)
                    setTextColor(Color.GREEN)
                }
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun stopGpsUpdates() {
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                false
            }
        } else {
            true
        }
    }
}
