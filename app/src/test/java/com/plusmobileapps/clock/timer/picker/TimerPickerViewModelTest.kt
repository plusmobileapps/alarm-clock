package com.plusmobileapps.clock.timer.picker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import com.plusmobileapps.clock.util.TestUtils
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TimerPickerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TimerPickerViewModel
    private lateinit var repository: TimerRepository
    private lateinit var observer: Observer<Unit>

    @Before
    fun setUp() {
        repository = mock(TimerRepository::class.java)
        observer = mock(Observer::class.java) as Observer<Unit>
        viewModel = TimerPickerViewModel(repository)
    }

    @Test
    fun testGetSeconds() {
        val seconds = viewModel.getSeconds()
        assert(seconds is LiveData<String>)
    }

    @Test
    fun testGetMinutes() {
        val minutes = viewModel.getMinutes()
        assert(minutes is LiveData<String>)
    }

    @Test
    fun testGetHours() {
        val hours = viewModel.getHours()
        assert(hours is LiveData<String>)
    }

    @Test
    fun onNumberClicked() {
        testNumberClicking()
    }

    @Test
    fun onNumberClickedStartingWithAZero() {
        viewModel.onNumberClicked(0)
        testNumberClicking()
    }

    private fun testNumberClicking() {
        viewModel.onNumberClicked(1)
        assert(viewModel.getSeconds().value == "01")
        viewModel.onNumberClicked(2)
        assert(viewModel.getSeconds().value == "12")
        viewModel.onNumberClicked(3)
        assert(viewModel.getMinutes().value == "01")
        assert(viewModel.getSeconds().value == "23")
        viewModel.onNumberClicked(4)
        assert(viewModel.getMinutes().value == "12")
        assert(viewModel.getSeconds().value == "34")
        viewModel.onNumberClicked(5)
        assert(viewModel.getHours().value == "01")
        assert(viewModel.getMinutes().value == "23")
        assert(viewModel.getSeconds().value == "45")
        viewModel.onNumberClicked(6)
        assert(viewModel.getHours().value == "12")
        assert(viewModel.getMinutes().value == "34")
        assert(viewModel.getSeconds().value == "56")
        viewModel.onNumberClicked(7)
        assert(viewModel.getHours().value == "12")
        assert(viewModel.getMinutes().value == "34")
        assert(viewModel.getSeconds().value == "56")
    }

    @Test
    fun onDeleteClicked() {
        primeStack()
        viewModel.onDeleteClicked()
        assert(viewModel.getSeconds().value == "01")
        viewModel.onDeleteClicked()
        assert(viewModel.getSeconds().value == "00")
    }

    @Test
    fun onTimerStartedFabClickSuccess() {
        primeStack()
        viewModel.closeTimerPicker.observe(TestUtils.TEST_OBSERVER, observer)
        viewModel.onTimerStartedFabClick()
        verify(repository).saveTimer(Timer(startTimeMillis = 12000, originalStartTimeMillis = 12000, timerText = "TODO: replace with timer text"))
        verify(observer).onChanged(null)
    }

    @Test
    fun onTimerStartedFabClickError() {
        viewModel.snackbarError.observe(TestUtils.TEST_OBSERVER, observer)
        viewModel.onTimerStartedFabClick()
        verify(observer).onChanged(null)
    }

    private fun primeStack() {
        viewModel.onNumberClicked(1)
        viewModel.onNumberClicked(2)
    }
}