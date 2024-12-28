package com.theberdakh.fitness.feature.common.error

import android.view.View

interface ErrorDelegate {
    fun errorToast(message: String)
    fun errorSnackbar(view: View, message: String, action: (() -> Unit)? = null)
}