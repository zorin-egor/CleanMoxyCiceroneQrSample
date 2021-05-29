package com.sample.qr.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Activity.startDefaultActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Context.startClearActivity() {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

inline fun <reified T : Activity> Context.startSingleTopActivity() {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    })
}

inline fun <reified T : Activity> Context.startSingleTaskActivity() {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}