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
}