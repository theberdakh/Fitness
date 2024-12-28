package com.theberdakh.fitness.feature.common.error

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.theberdakh.fitness.R

class ErrorDelegateImpl(
    private val context: Context
): ErrorDelegate {
    override fun errorToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    override fun errorSnackbar(view: View, message: String, action: (() -> Unit)?) =
        Snackbar.make(context, view, message, Snackbar.LENGTH_SHORT).apply {
            action?.let {
                setAction(context.getString(R.string.retry)) { it() }
            }
        }.show()
}