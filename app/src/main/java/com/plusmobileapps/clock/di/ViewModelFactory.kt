package com.plusmobileapps.clock.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.data.AlarmRepository
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Provider
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

////@Suppress("UNCHECKED_CAST")
//@Singleton
//class ViewModelFactory @Inject constructor(
//        private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
//
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(AlarmLandingViewModel::class.java)) {
//            AlarmLandingViewModel(alarmRepository) as T
//        } else {
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
//}


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

    //Add more ViewModels here
}

