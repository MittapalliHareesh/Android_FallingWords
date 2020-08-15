package com.game.fallingwords.di.modules

import com.game.fallingwords.FallingWordsActivity
import com.game.fallingwords.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
open abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun binding(): FallingWordsActivity
}