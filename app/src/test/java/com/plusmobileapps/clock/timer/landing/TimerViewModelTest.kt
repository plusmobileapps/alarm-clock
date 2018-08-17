package com.plusmobileapps.clock.timer.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class TimerViewModelTest {

    @get:Rule
    private val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TimerViewModel
    private lateinit var repository: TimerRepository

    @Before
    fun setUp() {
        repository = mock(TimerRepository::class.java)
        `when`(repository.getTimers()).thenReturn(MutableLiveData<List<Timer>>().apply {
            value = listOf(Timer(id = 0, startTimeMillis = 0L, currentTimeLeftMillis = 288L))
        })
        viewModel = TimerViewModel(repository)
    }

    @Test
    fun getTimers() {
        assert(false)
    }

    @Test
    fun getTimerClicked() {
        assert(false)
    }

    @Test
    fun getTimePickerTime() {
        assert(false)
    }

    @Test
    fun timerAddClicked() {
        assert(false)
    }

    @Test
    fun addTimer() {
        assert(false)
    }

    @Test
    fun deleteTimer() {
        assert(false)
    }

    @Test
    fun resetTimer() {
        assert(false)
    }

    @Test
    fun toggleTimer() {
        assert(false)
    }
}