package com.plusmobileapps.clock.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.main.MainActivityViewModel
import com.plusmobileapps.clock.timer.landing.TimerViewModel
import com.plusmobileapps.clock.timer.picker.TimerPickerViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlarmLandingViewModel::class)
    internal abstract fun alarmLandingViewModel(viewModel: AlarmLandingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimerViewModel::class)
    internal abstract fun timerViewModel(viewModel: TimerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimerPickerViewModel::class)
    internal abstract fun timerPickerViewModel(viewmodel: TimerPickerViewModel): ViewModel
}

