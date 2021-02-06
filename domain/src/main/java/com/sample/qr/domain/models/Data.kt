package com.sample.qr.domain.models

sealed class Data<out T>

data class Success<T>(val value: T) : Data<T>()

object Empty : Data<Nothing>()

data class Error(val error: String? = null) : Data<Nothing>()