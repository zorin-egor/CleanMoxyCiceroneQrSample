package com.sample.qr.domain.repositories

interface PreferenceRepository {

    fun logout()

    fun isAgreement(): Boolean

    fun setAgreement(value: Boolean)

    fun setParticipantName(value: String)

    fun getParticipantName(): String?

    fun setParticipantSurname(value: String)

    fun getParticipantSurname(): String?

    fun setParticipantEmail(value: String)

    fun getParticipantEmail(): String?

    fun getParticipantFullName(): String?

    fun setToken(value: String)

    fun getToken(): String?

    fun setParticipantLogin(value: Boolean)

    fun isParticipantLogin(): Boolean

}