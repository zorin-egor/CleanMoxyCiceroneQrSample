package com.sample.qr.data.services.mappers

import com.sample.qr.data.services.models.Event

internal fun mapTo(value: Event): com.sample.qr.domain.models.Event {
    return com.sample.qr.domain.models.Event(
            value.header,
            value.title,
            value.descriptions.let(::mapTo)
    )
}