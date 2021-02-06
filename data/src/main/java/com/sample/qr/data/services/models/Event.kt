package com.sample.qr.data.services.models

internal data class Event(
    val header: String,
    val title: String,
    val descriptions: List<EventDescription>
)
