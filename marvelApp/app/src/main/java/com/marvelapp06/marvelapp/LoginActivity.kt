package com.marvelapp06.marvelapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.marvelapp06.marvelapp.utils.NetworkConnection
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                login_nav_host_fragment_container.visibility = View.VISIBLE
                layoutDisconnectedLogin.visibility = View.GONE
            } else {
                login_nav_host_fragment_container.visibility = View.GONE
                layoutDisconnectedLogin.visibility = View.VISIBLE
            }
        })
    }
}