package com.greylabsdev.pexwalls.presentation.screen.splash

import com.greylabsdev.pexwalls.domain.usecase.SampleUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel

class SplashViewModel(private val sampleUseCase: SampleUseCase) : BaseViewModel() {
    fun test(): String = sampleUseCase.test()
}