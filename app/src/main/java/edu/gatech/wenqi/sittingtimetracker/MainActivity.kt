package edu.gatech.wenqi.sittingtimetracker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.absoluteValue

class MainActivity: AppCompatActivity(), SensorEventListener {
    private var vertUnitVec: FloatArray = FloatArray(3)
    private var acceleration: FloatArray = FloatArray(3)
    private var prevAcc: Float = 0.0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        val gravitySensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        // Register self as event listener
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
    }

    // Implement SensorEventListener interface
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val sensor:Sensor = it.sensor
            when(sensor.type) {
                Sensor.TYPE_LINEAR_ACCELERATION -> this.acceleration = it.values
                Sensor.TYPE_GRAVITY -> this.vertUnitVec = it.values.map { v -> (v / 9.81).toFloat() }.toFloatArray()
            }
        }

        val curAcc: Float = vertUnitVec[0] * acceleration[0] + vertUnitVec[1] * acceleration[1] + vertUnitVec[2] * acceleration[2]
        Log.i("acceleration", "$curAcc")
        if (curAcc.absoluteValue > 3.0) { // Threshold??
            if (prevAcc >= 0 && curAcc < 0) {
                textView.text = "위"
            } else if (prevAcc <= 0 && curAcc > 0) {
                textView.text = "아래"
            }
            prevAcc = curAcc
        }
    }

}