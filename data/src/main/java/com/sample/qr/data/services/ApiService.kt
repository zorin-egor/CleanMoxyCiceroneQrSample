package com.sample.qr.data.services

import com.sample.qr.data.services.models.*

internal interface ApiService {

    suspend fun register(name: String, surname: String, mail: String, captcha: String): Response<Token>

    suspend fun getCaptcha(): Response<Captcha>

    suspend fun getEvent(): Response<Event>

    suspend fun getQr(): Response<String>

    suspend fun login(login: String, pwd: String): Response<Token>

    suspend fun isValidQr(value: String): Response<QrValidate>

}