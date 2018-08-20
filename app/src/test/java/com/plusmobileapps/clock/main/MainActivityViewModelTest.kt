package com.plusmobileapps.clock.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.util.TestUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


class MainActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var observer: Observer<Unit>
    @Before
    fun setup() {
        viewModel = MainActivityViewModel()
        observer = mock(Observer::class.java) as Observer<Unit>
    }

    @Test
    fun testNavigation() {
        assert(viewModel.navigationClicked(R.id.navigation_alarm) == true)
        assert(viewModel.navigationClicked(R.id.navigation_timer) == true)
        assert(viewModel.navigationClicked(R.id.navigation_stopwatch) == true)
        assert(viewModel.navigationClicked(R.id.nav_host_fragment) == false)
    }

    @Test
    fun testIsNotCurrentScreenWithPreviousScreenSelected() {
        viewModel.closeBottomDrawer.observe(TestUtils.TEST_OBSERVER, observer)
        viewModel.isNotCurrentScreen(ViewState.Alarm)
        assertEquals(ViewState.Alarm, viewModel.getViewStateLiveData().value)
        verify(observer).onChanged(null)
    }

    @Test
    fun testIsNotCurrentScreenWithOtherScreenSelected() {
        viewModel.closeBottomDrawer.observe(TestUtils.TEST_OBSERVER, observer)
        viewModel.isNotCurrentScreen(ViewState.StopWatch)
        assertEquals(ViewState.StopWatch, viewModel.getViewStateLiveData().value)
        verify(observer).onChanged(null)
    }

    @Test
    fun testAddTimerClicked() {
        viewModel.addTimerClicked()
        assertEquals(ViewState.TimerPicker, viewModel.getViewStateLiveData().value)
    }

    @Test
    fun testTimerPickerFinished() {
        viewModel.addTimerClicked()
        viewModel.timerPickerFinished()
        assertEquals(ViewState.Timer, viewModel.getViewStateLiveData().value)
    }

    @Test
    fun testOnBackKeyPressedHome() {
        viewModel.killApp.observe(TestUtils.TEST_OBSERVER, observer)
        viewModel.onBackKeyPressed()
        verify(observer).onChanged(null)
    }

    @Test
    fun testOnBackKeyPressedTimerPicker() {
        assertEquals(ViewState.Alarm, viewModel.getViewStateLiveData().value)
        viewModel.addTimerClicked()
        viewModel.onBackKeyPressed()
        assertEquals(ViewState.Timer, viewModel.getViewStateLiveData().value)
    }

}