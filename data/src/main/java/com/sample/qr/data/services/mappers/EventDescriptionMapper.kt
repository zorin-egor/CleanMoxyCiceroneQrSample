package com.sample.qr.data.services.mappers

import com.sample.qr.data.services.models.EventDescription

internal fun mapTo(value: EventDescription): com.sample.qr.domain.models.EventDescription {
    return com.sample.qr.domain.models.EventDescription(
        value.imageUrl,
        value.description
    )
}

internal fun mapTo(value: List<EventDescription>): List<com.sample.qr.domain.models.EventDescription> {
    return value.map(::mapTo)
}