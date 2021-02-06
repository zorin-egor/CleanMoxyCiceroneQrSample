package com.sample.qr.data.services.mappers

import com.sample.qr.data.services.models.Error
import com.sample.qr.data.services.models.Response
import com.sample.qr.data.services.models.Success
import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Empty

internal fun <A, B> Response<A>.convertTo(mapper: (A) -> B): Data<B> {
    return when(this) {
        is Success -> value?.let(mapper)
                ?.let { com.sample.qr.domain.models.Success(it) }
                ?: Empty
        is Error -> com.sample.qr.domain.models.Error(error)
    }
}