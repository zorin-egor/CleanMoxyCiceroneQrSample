package com.sample.qr.presentation.ui.screens.common.adapters.models

import android.graphics.Bitmap

sealed class BaseAboutItem

open class TextAboutItem(val text: String) : BaseAboutItem()

class HeaderAboutItem(text: String) : TextAboutItem(text)

class ImageAboutItem(val bitmap: Bitmap) : BaseAboutItem()