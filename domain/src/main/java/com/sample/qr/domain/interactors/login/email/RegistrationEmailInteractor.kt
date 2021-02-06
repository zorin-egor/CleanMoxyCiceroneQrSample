package com.sample.qr.domain.interactors.login.email

import com.sample.qr.domain.models.Data

interface RegistrationEmailInteractor {

    suspend fun register(name: String, surname: String, mail: String, captcha: String): Data<Boolean>

    suspend fun getCaptcha(): Data<String>

}