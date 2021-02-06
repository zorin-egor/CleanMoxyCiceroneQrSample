package com.sample.qr.data.services

import android.app.Application
import com.sample.qr.data.R
import com.sample.qr.data.services.models.*
import com.sample.qr.domain.exceptions.AuthException
import com.sample.qr.domain.exceptions.ConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

internal class ApiServiceImpl @Inject constructor(
    private val app: Application
): ApiService {

    private var mCount: Int = 0
    private var mCaptcha: Captcha? = null

    private suspend fun <T> handler(isErrors: Boolean = true, isDelay: Boolean = true, action: () -> T): Response<T> {
        return withContext(Dispatchers.IO) {
            try {
                if (isDelay) {
                    delay(2000)
                }
                ++mCount
                when {
                    isErrors && mCount % 3 == 0 -> throw AuthException("Auth error")
                    isErrors && mCount % 7 == 0 -> throw ConnectionException("Connection error")
                    isErrors && mCount % 11 == 0 -> throw Exception("Unexpected error $mCount")
                    else -> Success(action())
                }
            } catch (e: Exception) {
                Error(e.message ?: "unknown")
            }
        }
    }

    override suspend fun register(name: String, surname: String, mail: String, captcha: String): Response<Token> {
        return if (captcha != mCaptcha?.captcha) {
            Error("Wrong captcha")
        } else {
            handler {
                Token(UUID.randomUUID().toString())
            }
        }
    }

    override suspend fun getCaptcha(): Response<Captcha> {
        return handler {
            Captcha(UUID.randomUUID().toString().substring(0, 5)).also {
                mCaptcha = it
            }
        }
    }

    override suspend fun getEvent(): Response<Event> {
        return handler(false, false) {
            Event(
                app.getString(R.string.about_header),
                app.getString(R.string.about_title),
                arrayListOf(
                        EventDescription(
                                null,
                                app.getString(R.string.about_description)
                        ),
                        EventDescription(
                                "",
                                app.getString(R.string.about_description)
                        )
                )
            )
        }
    }

    override suspend fun getQr(): Response<String> {
        return Success(app.getString(R.string.qr_data))
    }

    override suspend fun login(login: String, pwd: String): Response<Token> {
        return handler {
            Token(UUID.randomUUID().toString())
        }
    }

    override suspend fun isValidQr(value: String): Response<QrValidate> {
        return handler {
            QrValidate(Random.nextBoolean())
        }
    }
}