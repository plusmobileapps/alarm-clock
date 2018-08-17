package com.plusmobileapps.clock

import com.plusmobileapps.clock.alarm.AlarmLandingViewModelTest
import com.plusmobileapps.clock.main.MainActivityViewModelTest
import com.plusmobileapps.clock.timer.landing.TimerViewModelTest
import com.plusmobileapps.clock.timer.picker.TimerPickerViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        TimerPickerViewModelTest::class,
        MainActivityViewModelTest::class,
        TimerViewModelTest::class,
        AlarmLandingViewModelTest::class)
class ViewModelTestSuite {
}