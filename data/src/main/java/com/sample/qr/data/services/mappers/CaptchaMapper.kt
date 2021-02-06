package com.sample.qr.data.services.mappers

import com.sample.qr.data.services.models.Captcha

internal fun mapTo(value: Captcha): String {
    return value.captcha
}