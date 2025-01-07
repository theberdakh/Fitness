package com.theberdakh.fitness.feature.common.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.button.MaterialButton
import com.theberdakh.fitness.R

class UniversalDialog private constructor(
    private val context: Context,
    private val title: String,
    private val message: String = "",
    private val primaryButtonText: String = "",
    private val secondaryButtonText: String = "",
    private val primaryButtonColor: Int = context.getColor(android.R.color.holo_red_light),
    private val onPrimaryButtonClick: (() -> Unit)?,
    private val onSecondaryButtonClick: (() -> Unit)?,
    private val cancelable: Boolean = true,
    private val titleTextSize: Float = 20f,
    private val messageTextSize: Float = 14f,
    private val buttonTextSize: Float = 16f
) {
    private var dialog: Dialog? = null

    class Builder(private val context: Context) {
        private var title: String = ""
        private var message: String = ""
        private var primaryButtonText: String = ""
        private var secondaryButtonText: String = ""
        private var primaryButtonColor: Int = context.getColor(android.R.color.holo_red_light)
        private var onPrimaryButtonClick: (() -> Unit)? = null
        private var onSecondaryButtonClick: (() -> Unit)? = null
        private var cancelable: Boolean = true
        private var titleTextSize: Float = 20f
        private var messageTextSize: Float = 14f
        private var buttonTextSize: Float = 16f

        fun setTitle(title: String) = apply { this.title = title }
        fun setMessage(message: String) = apply { this.message = message }
        fun setPrimaryButton(text: String, onClick: (() -> Unit)? = null) = apply {
            this.primaryButtonText = text
            this.onPrimaryButtonClick = onClick
        }
        fun setSecondaryButton(text: String, onClick: (() -> Unit)? = null) = apply {
            this.secondaryButtonText = text
            this.onSecondaryButtonClick = onClick
        }

        fun build() = UniversalDialog(
            context, title, message, primaryButtonText, secondaryButtonText,
            primaryButtonColor, onPrimaryButtonClick, onSecondaryButtonClick,
            cancelable, titleTextSize, messageTextSize, buttonTextSize
        )
    }

    fun show() {
        dialog?.dismiss()

        dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(createDialogView())
            setCancelable(cancelable)

            // Set dialog width to 90% of screen width
            window?.apply {
                setBackgroundDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.bg_universal_dialog, null))
                val width = (context.resources.displayMetrics.widthPixels * 0.9).toInt()
                setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
            }
        }
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }

    private fun createDialogView(): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(24), dpToPx(24), dpToPx(24), dpToPx(24))

            // Title
            addView(TextView(context).apply {
                text = title
                setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize)
                setTextColor(Color.BLACK)
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = dpToPx(8)
                }
            })

            // Message (if provided)
            message.let { messageText ->
                addView(TextView(context).apply {
                    text = messageText
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, messageTextSize)
                    setTextColor(Color.GRAY)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = dpToPx(24)
                    }
                })
            }

            // Primary Button
            addView(MaterialButton(context).apply {
                text = primaryButtonText
                setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize)
                setBackgroundColor(primaryButtonColor)
                cornerRadius = dpToPx(8)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = dpToPx(8)
                }
                setOnClickListener {
                    onPrimaryButtonClick?.invoke()
                    dismiss()
                }
            })

            // Secondary Button (if provided)
            secondaryButtonText.let { secondaryText ->
                if (secondaryText.isNotEmpty()){
                    addView(TextView(context).apply {
                        text = secondaryText
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize)
                        setTextColor(Color.GRAY)
                        gravity = Gravity.CENTER
                        setPadding(0, dpToPx(12), 0, dpToPx(12))
                        background = context.getSelectableItemBackground()
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        setOnClickListener {
                            onSecondaryButtonClick?.invoke()
                            dismiss()
                        }
                    })
                }

            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    private fun Context.getSelectableItemBackground(): Drawable? {
        val outValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        return ContextCompat.getDrawable(this, outValue.resourceId)
    }
}