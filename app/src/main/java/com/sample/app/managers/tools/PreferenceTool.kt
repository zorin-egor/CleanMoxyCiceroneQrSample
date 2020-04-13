package com.sample.app.managers.tools

import android.content.Context
import android.content.SharedPreferences


class PreferenceTool(context: Context) {

    companion object {
        val TAG = PreferenceTool.javaClass.simpleName
    }

    private val KEY_1 = "KEY_1"
    private val KEY_2 = "KEY_2"
    private val KEY_3 = "KEY_3"
    private val KEY_4 = "KEY_4"

    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }

    fun isAgreement(): Boolean = mSharedPreferences.getBoolean(KEY_1, false)

    fun setAgreement(value: Boolean) = mSharedPreferences.edit().putBoolean(KEY_1, value).commit()

    fun setParticipantName(value: String) = mSharedPreferences.edit().putString(KEY_2, value).commit()

    fun getParticipantName() = mSharedPreferences.getString(KEY_2, null)

    fun setParticipantSurname(value: String) = mSharedPreferences.edit().putString(KEY_3, value).commit()

    fun getParticipantSurname() = mSharedPreferences.getString(KEY_3, null)

    fun setParticipantEmail(value: String) = mSharedPreferences.edit().putString(KEY_4, value).commit()

    fun getParticipantEmail() = mSharedPreferences.getString(KEY_4, null)

    fun getParticipantFullName() = "${getParticipantName()} ${getParticipantSurname()}"

}