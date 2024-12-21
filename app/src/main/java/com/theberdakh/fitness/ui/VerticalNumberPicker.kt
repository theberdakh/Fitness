package com.theberdakh.fitness.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.abs

// CustomNumberPicker.kt
class VerticalNumberPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var numbers = (140..240).toList()
    private var selectedPosition = numbers.size / 2
    private val itemHeight = 50.dpToPx(context)
    private val visibleItems = 5

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 16.dpToPx(context).toFloat()
        typeface = Typeface.DEFAULT
    }

    private var scrollOffset = 0f
    private var animator: ValueAnimator? = null

    init {
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = (itemHeight * visibleItems).toInt()
        val height = resolveSize(desiredHeight, heightMeasureSpec)
        val width = resolveSize(200.dpToPx(context), widthMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // Draw the center line (selected item)
        paint.color = Color.parseColor("#E0E0E0")
        canvas.drawLine(
            0f,
            centerY,
            width.toFloat(),
            centerY,
            paint
        )

        for (i in -2..2) {
            val position = selectedPosition + i
            if (position in numbers.indices) {
                val y = centerY + (i * itemHeight) - scrollOffset
                val alpha = ((1f - abs(y - centerY) / (height / 2f)) * 255).toInt()
                paint.alpha = alpha.coerceIn(0, 255)
                paint.color = if (y == centerY) Color.BLACK else Color.GRAY
                canvas.drawText(
                    "${numbers[position]} см",
                    centerX,
                    y + itemHeight / 2,
                    paint
                )
            }
        }
    }

    private var lastY = 0f
    private var isDragging = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = event.y
                isDragging = true
                animator?.cancel()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val dy = event.y - lastY
                    scrollOffset += dy

                    // Update selected position based on scroll
                    while (scrollOffset > itemHeight) {
                        if (selectedPosition > 0) {
                            selectedPosition--
                            scrollOffset -= itemHeight
                        } else {
                            scrollOffset = itemHeight.toFloat()
                        }
                    }
                    while (scrollOffset < -itemHeight) {
                        if (selectedPosition < numbers.size - 1) {
                            selectedPosition++
                            scrollOffset += itemHeight
                        } else {
                            scrollOffset = (-itemHeight).toFloat()
                        }
                    }

                    lastY = event.y
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (isDragging) {
                    isDragging = false
                    // Animate to nearest position
                    animator = ValueAnimator.ofFloat(scrollOffset, 0f).apply {
                        duration = 200
                        interpolator = DecelerateInterpolator()
                        addUpdateListener {
                            scrollOffset = it.animatedValue as Float
                            invalidate()
                        }
                        start()
                    }
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun getSelectedNumber(): Int = numbers[selectedPosition]

    private fun Int.dpToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()
}