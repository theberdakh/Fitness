package com.theberdakh.fitness.feature.common.error

import android.view.View

interface ErrorDelegate {
    fun errorToast(message: String)
    fun errorSnackbar(view: View, message: String, actionText: String, action: (() -> Unit)? = null)
    fun errorSnackbarLoading(
        view: View,
        message: String,
        loadingAction: (() -> Unit)
    )
}