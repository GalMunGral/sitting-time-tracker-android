package edu.gatech.wenqi.sittingtimetracker

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class MainActivity: FragmentActivity() {

    lateinit var requestQueue: RequestQueue
    var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.requestQueue = Volley.newRequestQueue(this)

        val transaction = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        transaction.add(R.id.root, loginFragment)
        transaction.commit()

        Intent(this, SensorService::class.java).also {
            startService(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Intent(this, SensorService::class.java).also {
            stopService(it)
        }
    }

    fun onLoginSuccess(token: String) {
        this.token = token
        val testFragment = TestFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root, testFragment)
        transaction.addToBackStack("test")
        transaction.commit()
    }

}