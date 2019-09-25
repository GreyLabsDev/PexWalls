package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.domain.repository.IRepository

class SampleUseCase(private val repository: IRepository) {
    fun test() = "test"
}