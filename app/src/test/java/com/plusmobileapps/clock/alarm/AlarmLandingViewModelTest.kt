package com.plusmobileapps.clock.alarm

import com.plusmobileapps.clock.FirebaseAuthHelper
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.data.alarm.AlarmRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AlarmLandingViewModelTest {

    private lateinit var viewmodel: AlarmLandingViewModel
    private lateinit var alarmRepository: AlarmRepository
    private lateinit var firebaseAuthHelper: FirebaseAuthHelper

    @Before
    fun setup() {
        alarmRepository = mock(AlarmRepository::class.java)
        firebaseAuthHelper = mock(FirebaseAuthHelper::class.java)
        viewmodel = AlarmLandingViewModel(alarmRepository, firebaseAuthHelper)
    }

    @Test
    fun testAddAlarm() {
        viewmodel.addAlarm(12, 12)
        verify(alarmRepository).saveAlarm(Alarm(hour = 12, min =  12))
    }

    @Test
    fun testUpdateAlarm() {
        val alarm = Alarm(hour = 12, min = 12)
        viewmodel.updateAlarm(alarm)
        verify(alarmRepository).saveAlarm(alarm)
    }

    @Test
    fun testUpdateAlarmToggle() {

    }
}