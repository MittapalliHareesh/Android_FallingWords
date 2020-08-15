package com.game.fallingwords.baseclasses

import android.content.Intent
import dagger.android.DaggerActivity

open class BaseActivity : DaggerActivity() {

    protected fun startNewActivity(cls:Class<*>){
        startActivity(Intent(this,cls))
    }
}