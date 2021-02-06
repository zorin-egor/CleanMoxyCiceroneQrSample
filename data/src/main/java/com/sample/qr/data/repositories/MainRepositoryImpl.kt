package com.sample.qr.data.repositories

import com.sample.qr.data.services.ApiService
import com.sample.qr.data.services.mappers.convertTo
import com.sample.qr.data.services.mappers.mapTo
import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Error
import com.sample.qr.domain.models.Event
import com.sample.qr.domain.models.Success
import com.sample.qr.domain.repositories.MainRepository
import com.sample.qr.domain.repositories.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MainRepositoryImpl @Inject constructor(
        private val apiService: ApiService,
        private val preferenceRepository: PreferenceRepository,
) : MainRepository {

    override suspend fun logout() {
        preferenceRepository.logout()
    }

    override suspend fun register(name: String, surname: String, mail: String, captcha: String): Data<Boolean> {
        return withContext(Dispatchers.IO) {
            apiService.register(name, surname, mail, captcha).also { response ->
                if (response is com.sample.qr.data.services.models.Success && response.value != null) {
                    preferenceRepository.setToken(response.value.token)
                    preferenceRepository.setParticipantName(name)
                    preferenceRepository.setParticipantSurname(surname)
                    preferenceRepository.setParticipantEmail(mail)
                }
            }.convertTo(::mapTo)
        }
    }

    override suspend fun getCaptcha(): Data<String> {
        return apiService.getCaptcha().convertTo(::mapTo)
    }

    override suspend fun getEvent(): Data<Event> {
        return apiService.getEvent().convertTo(::mapTo)
    }

    override suspend fun getQr(): Data<String> {
        return apiService.getQr().convertTo { it }
    }

    override suspend fun getParticipantName(): Data<String> {
        return withContext(Dispatchers.IO) {
            preferenceRepository.getParticipantName()
                    ?.let { Success(it) }
                    ?: Error()
        }
    }

    override suspend fun setParticipantLogin(value: Boolean) {
        return preferenceRepository.setParticipantLogin(value)
    }

    override suspend fun login(login: String, pwd: String): Data<Boolean> {
        return withContext(Dispatchers.IO) {
            apiService.login(login, pwd).also { response ->
                if (response is com.sample.qr.data.services.models.Success && response.value != null) {
                    preferenceRepository.setToken(response.value.token)
                }
            }.convertTo(::mapTo)
        }
    }

    override suspend fun isValidQr(value: String): Data<Boolean> {
        return withContext(Dispatchers.IO) {
            apiService.isValidQr(value).convertTo(::mapTo)
        }
    }
}