package com.edsonlima.flixapp.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding

fun onApplyWindowInsets(
    view: View,
    applyTop: Boolean = true,
    applyBottom: Boolean = true
) {

    ViewCompat.setOnApplyWindowInsetsListener(view) { view, windowsInsets ->
        val insets = windowsInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = if (applyTop) insets.top else 0
            bottomMargin = if (applyBottom) insets.bottom else 0
        }
        WindowInsetsCompat.CONSUMED
    }
}

fun onApplyComponentWindowInsets(
    view: View,
    applyTop: Boolean = true,
    applyBottom: Boolean = true
) {

    ViewCompat.setOnApplyWindowInsetsListener(view) { view, windowsInsets ->
        val insets =
            windowsInsets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
        view.updatePadding(
            top = if (applyTop) insets.top else 0,
            bottom = if (applyBottom) insets.bottom else 0
        )
        WindowInsetsCompat.CONSUMED
    }
}