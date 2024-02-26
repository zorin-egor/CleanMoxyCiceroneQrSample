package com.sample.qr.domain.usecases.captcha

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.repositories.MainRepository

internal class CaptchaUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(): Data<String> {
        return repository.getCaptcha()
    }

}