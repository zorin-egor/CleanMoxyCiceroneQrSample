package com.sample.qr.data.services.mappers

import com.sample.qr.data.services.models.QrValidate

internal fun mapTo(value: QrValidate): Boolean {
    return value.isValid
}