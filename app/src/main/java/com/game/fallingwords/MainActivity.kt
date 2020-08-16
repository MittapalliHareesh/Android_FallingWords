package com.game.fallingwords

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        startBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in))
        startBtn.setOnClickListener{
            if (haveNetwork()) {
                startActivity(Intent(this, FallingWordsActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.networkErrorMes),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun haveNetwork(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetworkInfo
        return network?.isConnectedOrConnecting == true
    }
}