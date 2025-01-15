package com.theberdakh.fitness.feature.common.error

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.theberdakh.fitness.R

class ErrorDelegateImpl(
    private val context: Context
) : ErrorDelegate {
    override fun errorToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun errorSnackbar(
        view: View,
        message: String,
        actionText: String,
        action: (() -> Unit)?
    ) {
        context.setTheme(R.style.Theme_Fitness)
        Snackbar.make(context, view, message, Snackbar.LENGTH_SHORT).apply {
            action?.let {
                setAction(actionText) { action() }
            }
        }.show()
    }

    /** Snackbar with pre-defined values and action
     * @param loadingAction -> how to load  */
    override fun errorSnackbarLoading(
        view: View,
        message: String,
        loadingAction: (() -> Unit)
    ) {
        context.setTheme(R.style.Theme_Fitness)
        Snackbar.make(context, view, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(context.getString(R.string.retry)) { loadingAction() }
        }.show()
    }
}