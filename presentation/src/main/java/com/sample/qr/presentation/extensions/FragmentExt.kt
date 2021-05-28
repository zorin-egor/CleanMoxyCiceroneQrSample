package com.sample.qr.presentation.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.show(
    fragment: Fragment,
    @IdRes container: Int,
    isAdd: Boolean = false,
    tag: String? = fragment.javaClass.simpleName
) {
    beginTransaction().apply {
        if (isAdd) {
            add(container, fragment, tag)
        } else {
            replace(container, fragment, tag)
        }

        tag?.let { addToBackStack(it) }

    }.commit()
}

fun FragmentManager.showRoot() {
    popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun FragmentManager.back() {
    popBackStack()
}
