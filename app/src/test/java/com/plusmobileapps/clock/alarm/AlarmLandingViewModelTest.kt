package com.plusmobileapps.clock.alarm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.plusmobileapps.clock.FirebaseAuthHelper
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.data.alarm.AlarmRepository
import com.plusmobileapps.clock.util.TestUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class AlarmLandingViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewmodel: AlarmLandingViewModel
    private lateinit var alarmRepository: AlarmRepository
    private lateinit var firebaseAuthHelper: FirebaseAuthHelper
    private lateinit var showTimePickerObserver: Observer<Alarm>
    private lateinit var openAlarmObserver: Observer<Int>

    @Before
    fun setup() {
        showTimePickerObserver = mock(Observer::class.java) as Observer<Alarm>
        openAlarmObserver = mock(Observer::class.java) as Observer<Int>
        alarmRepository = mock(AlarmRepository::class.java)
        firebaseAuthHelper = mock(FirebaseAuthHelper::class.java)
        val list = MutableLiveData<List<Alarm>>().apply {
            value = listOf<Alarm>(Alarm(id = 0, enabled = false))
        }
        `when`(alarmRepository.getAlarms()).thenReturn(list)

        viewmodel = AlarmLandingViewModel(alarmRepository)
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
        val beforeAlarm = viewmodel.getAlarms().value!![0]
        viewmodel.updateAlarmToggle(true, beforeAlarm)
        verify(alarmRepository).saveAlarm(beforeAlarm.apply { enabled = true })
    }

    @Test
    fun testShowTimePickerNew() {
        viewmodel.showTimePicker.observe(TestUtils.TEST_OBSERVER, showTimePickerObserver)
        viewmodel.showTimePicker()
        verify(showTimePickerObserver).onChanged(null)
    }

    @Test
    fun testShowTimePickerEdit() {
        viewmodel.showTimePicker.observe(TestUtils.TEST_OBSERVER, showTimePickerObserver)
        val alarm = Alarm(id = 1)
        viewmodel.showTimePicker(alarm)
        verify(showTimePickerObserver).onChanged(alarm)
    }

    @Test
    fun testDeleteAlarm() {
        val alarm = alarmRepository.getAlarms().value!![0]
        viewmodel.deleteAlarm(alarm)
        verify(alarmRepository).deleteAlarm(alarm)
    }

    @Test
    fun testOpenAlarm() {
        val alarm = Alarm(id = 1)
        viewmodel.openAlarm.observe(TestUtils.TEST_OBSERVER, openAlarmObserver)
        viewmodel.openAlarm(alarm)
        verify(openAlarmObserver).onChanged(1)
    }
}