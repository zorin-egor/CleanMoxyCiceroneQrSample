package com.sample.qr.data.repositories

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.sample.qr.domain.repositories.PreferenceRepository

internal class PreferenceRepositoryImpl(app: Application) : PreferenceRepository {

    companion object {
        private const val PREFERENCE_NAME = "APP_PREFERENCE"
    }

    private val KEY_1 = "KEY_1"
    private val KEY_2 = "KEY_2"
    private val KEY_3 = "KEY_3"
    private val KEY_4 = "KEY_4"
    private val KEY_5 = "KEY_5"
    private val KEY_6 = "KEY_6"

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = app.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun logout() {
        sharedPreferences.edit().clear().apply()
    }

    override fun isAgreement(): Boolean = sharedPreferences.getBoolean(KEY_1, false)

    override fun setAgreement(value: Boolean) = sharedPreferences.edit().putBoolean(KEY_1, value).apply()

    override fun setParticipantName(value: String) = sharedPreferences.edit().putString(KEY_2, value).apply()

    override fun getParticipantName() = sharedPreferences.getString(KEY_2, null)

    override fun setParticipantSurname(value: String) = sharedPreferences.edit().putString(KEY_3, value).apply()

    override fun getParticipantSurname() = sharedPreferences.getString(KEY_3, null)

    override fun setParticipantEmail(value: String) = sharedPreferences.edit().putString(KEY_4, value).apply()

    override fun getParticipantEmail() = sharedPreferences.getString(KEY_4, null)

    override fun getParticipantFullName() = "${getParticipantName()} ${getParticipantSurname()}"

    override fun setToken(value: String) = sharedPreferences.edit().putString(KEY_5, value).apply()

    override fun getToken(): String? = sharedPreferences.getString(KEY_5, null)

    override fun setParticipantLogin(value: Boolean) = sharedPreferences.edit().putBoolean(KEY_6, value).apply()

    override fun isParticipantLogin() = sharedPreferences.getBoolean(KEY_6, true)

}