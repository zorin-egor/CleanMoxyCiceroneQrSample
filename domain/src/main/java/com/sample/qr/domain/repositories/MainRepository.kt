package com.sample.qr.domain.repositories

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Event

interface MainRepository {

    suspend fun logout()

    suspend fun register(name: String, surname: String, mail: String, captcha: String): Data<Boolean>

    suspend fun getCaptcha(): Data<String>

    suspend fun getEvent(): Data<Event>

    suspend fun getQr(): Data<String>

    suspend fun getParticipantName(): Data<String>

    suspend fun setParticipantLogin(value: Boolean)

    suspend fun login(login: String, pwd: String): Data<Boolean>

    suspend fun isValidQr(value: String): Data<Boolean>

}