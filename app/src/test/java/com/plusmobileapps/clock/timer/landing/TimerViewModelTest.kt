package com.plusmobileapps.clock.timer.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import com.plusmobileapps.clock.util.TestUtils
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.*

class TimerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TimerViewModel
    private lateinit var repository: TimerRepository
    private lateinit var liveTimers: LiveData<List<Timer>>
    private lateinit var showTimerPickerObserver: Observer<Unit>
    private lateinit var timer: Timer

    @Before
    fun setUp() {
        showTimerPickerObserver = mock(Observer::class.java) as Observer<Unit>
        repository = mock(TimerRepository::class.java)
        timer = Timer(id = 0, startTimeMillis = 0L, originalStartTimeMillis = 2332L, timerText = "00:00")
        val list = listOf<Timer>(timer)
        liveTimers = MutableLiveData<List<Timer>>().apply {
            value = list
        }
        `when`(repository.getTimers()).thenReturn(liveTimers)
        viewModel = TimerViewModel(repository)
    }

    @Test
    fun getTimers() {
        val timers = viewModel.timers
        assert(timers == liveTimers)
    }

    @Test
    fun addTimerButtonClicked() {
        viewModel.showTimerPicker.observe(TestUtils.TEST_OBSERVER, showTimerPickerObserver)
        viewModel.addTimerButtonClicked()
        verify(showTimerPickerObserver).onChanged(null)
    }

    @Test
    fun deleteTimer() {
        viewModel.deleteTimer(timer)
        verify(repository).deleteTimer(timer)
    }

    @Test
    fun resetTimer() {
        viewModel.resetTimer(timer)
        verify(repository).saveTimer(timer)
    }

    @Test
    fun toggleTimer() {
        assert(false)
    }
}