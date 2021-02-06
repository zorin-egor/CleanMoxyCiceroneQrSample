package com.sample.qr.domain.models

data class Event(
    val header: String,
    val title: String,
    val descriptions: List<EventDescription>
)
