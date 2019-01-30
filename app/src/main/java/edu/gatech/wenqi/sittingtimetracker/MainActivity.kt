package edu.gatech.wenqi.sittingtimetracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        Intent(this, SensorService::class.java).also {
            startService(it)
        }
    }

}