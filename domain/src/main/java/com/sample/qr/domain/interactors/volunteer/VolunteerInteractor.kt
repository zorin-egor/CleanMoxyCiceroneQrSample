package com.sample.qr.domain.interactors.volunteer

import com.sample.qr.domain.models.Data

interface VolunteerInteractor {

    suspend fun logout()

    suspend fun login(login: String, pwd: String): Data<Boolean>

    suspend fun isValidQr(value: String): Data<Boolean>

}