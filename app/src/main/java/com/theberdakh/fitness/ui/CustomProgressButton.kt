package com.theberdakh.fitness.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.theberdakh.fitness.R

class CustomProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var progressCircle: CircularProgressDrawable
    private var buttonText: TextView
    private var progressBar: ProgressBar
    private var isLoading = false

    init {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.view_progress_button, this, true)

        // Initialize views
        buttonText = findViewById(R.id.buttonText)
        progressBar = findViewById(R.id.progressBar)

        // Setup circular progress
        progressCircle = CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 15f
            setColorSchemeColors(Color.WHITE)
        }

        // Hide progress initially
        progressBar.visibility = View.GONE
    }

    fun startLoading() {
        isLoading = true
        isEnabled = false
        buttonText.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        isLoading = false
        isEnabled = true
        buttonText.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun setText(text: String) {
        buttonText.text = text
    }
}