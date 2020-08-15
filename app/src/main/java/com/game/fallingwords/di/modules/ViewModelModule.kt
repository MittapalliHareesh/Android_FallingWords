package com.game.fallingwords.di.modules

import android.view.ContextMenu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.game.fallingwords.viewmodel.FallingWordViewModel
import com.game.fallingwords.di.util.ViewModelKey
import com.game.fallingwords.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FallingWordViewModel::class)
    abstract fun bindFallingWordViewModel(listViewModel: FallingWordViewModel): ViewModel


   /* @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory*/
   @Binds
   internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
