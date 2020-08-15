package com.game.fallingwords

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
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
            startActivity(Intent(this, FallingWordsActivity::class.java))
            finish()
        }
    }
}