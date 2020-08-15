package com.game.fallingwords.di.modules

import dagger.Module

@Module(includes = [ViewModelModule::class, NetworkModule::class])
class ApplicationModule