package com.sample.app.mvp.models

import android.graphics.Bitmap

abstract class BaseAboutItem

open class TextAboutItem(var text: String) : BaseAboutItem()

class HeaderAboutItem(text: String) : TextAboutItem(text)

class ImageAboutItem(var bitmap: Bitmap?) : BaseAboutItem()