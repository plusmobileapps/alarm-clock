package com.plusmobileapps.clock.timer.picker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
    fun onNumberClicked() {
        viewModel.onNumberClicked("1")
        assert(viewModel.getSeconds().value == 1)
        viewModel.onNumberClicked("2")
        assert(viewModel.getSeconds().value == 12)
        viewModel.onNumberClicked("3")
        assert(viewModel.getMinutes().value == 1)
        assert(viewModel.getSeconds().value == 23)
        viewModel.onNumberClicked("4")
        assert(viewModel.getMinutes().value == 12)
        assert(viewModel.getSeconds().value == 34)
        viewModel.onNumberClicked("5")
        assert(viewModel.getHours().value == 1)
        assert(viewModel.getMinutes().value == 23)
        assert(viewModel.getSeconds().value == 45)
        viewModel.onNumberClicked("6")
        assert(viewModel.getHours().value == 12)
        assert(viewModel.getMinutes().value == 34)
        assert(viewModel.getSeconds().value == 56)
        viewModel.onNumberClicked("5")
        assert(viewModel.getHours().value == 12)
        assert(viewModel.getMinutes().value == 34)
        assert(viewModel.getSeconds().value == 56)
    }

    @Test
    fun onNumberClickedStartingWithAZero() {
        viewModel.onNumberClicked("0")
        viewModel.onNumberClicked("1")
        assert(viewModel.getSeconds().value == 1)
        viewModel.onNumberClicked("2")
        assert(viewModel.getSeconds().value == 12)
        viewModel.onNumberClicked("3")
        assert(viewModel.getMinutes().value == 1)
        assert(viewModel.getSeconds().value == 23)
        viewModel.onNumberClicked("4")
        assert(viewModel.getMinutes().value == 12)
        assert(viewModel.getSeconds().value == 34)
        viewModel.onNumberClicked("5")
        assert(viewModel.getHours().value == 1)
        assert(viewModel.getMinutes().value == 23)
        assert(viewModel.getSeconds().value == 45)
        viewModel.onNumberClicked("6")
        assert(viewModel.getHours().value == 12)
        assert(viewModel.getMinutes().value == 34)
        assert(viewModel.getSeconds().value == 56)
        viewModel.onNumberClicked("5")
        assert(viewModel.getHours().value == 12)
        assert(viewModel.getMinutes().value == 34)
        assert(viewModel.getSeconds().value == 56)
    }

    @Test
    fun onDeleteClicked() {
        primeStack()
        viewModel.onDeleteClicked()
        assert(viewModel.getSeconds().value == 1)
        viewModel.onDeleteClicked()
        assert(viewModel.getSeconds().value == 0)
    }

    @Test
    fun onTimerStartedFabClickSuccess() {
        primeStack()
        viewModel.timerButtonStartEvent.observe(TestUtils.TEST_OBSERVER, observer)
        viewModel.onTimerStartedFabClick()
        verify(repository).saveTimer(Timer(startTimeMillis = 12000, currentTimeLeftMillis = 12000))
        verify(observer).onChanged(null)
    }

    @Test
    fun onTimerStartedFabClickError() {
        viewModel.snackbarError.observe(TestUtils.TEST_OBSERVER, observer)
        viewModel.onTimerStartedFabClick()
        verify(observer).onChanged(null)
    }

    @Test
    fun getDisplayTime() {
        val time1 = viewModel.getDisplayTime(0)
        assert(time1 == "00")
        val time2 = viewModel.getDisplayTime(1)
        assert(time2 == "01")
        val time3 = viewModel.getDisplayTime(10)
        assert(time3 == "10")
    }

    private fun primeStack() {
        viewModel.onNumberClicked("1")
        viewModel.onNumberClicked("2")
    }
}