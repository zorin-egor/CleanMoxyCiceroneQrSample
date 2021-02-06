package com.sample.qr.data.services.models

internal sealed class Response<out T>

internal data class Success<T>(val value: T? = null) : Response<T>()

internal data class Error(val error: String? = null) : Response<Nothing>()