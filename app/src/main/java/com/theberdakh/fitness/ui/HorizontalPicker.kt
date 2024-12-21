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

class HorizontalPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):  View(context, attrs, defStyleAttr){

    private var numbers = (55..65).toList()
    private var selectedPosition = numbers.size / 2
    private val itemWidth = 60.dpToPx(context)
    private val visibleItems = 5

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 18.dpToPx(context).toFloat()
        typeface = Typeface.DEFAULT
    }

    private var scrollOffset = 0f
    private var animator: ValueAnimator? = null

    init {
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = (itemWidth * visibleItems).toInt()
        val width = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(80.dpToPx(context), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // Draw numbers
        for (i in -2..2) {
            val position = selectedPosition + i
            if (position in numbers.indices) {
                val x = centerX + (i * itemWidth) - scrollOffset
                val alpha = ((1f - abs(x - centerX) / (width / 2f)) * 255).toInt()
                paint.alpha = alpha.coerceIn(0, 255)

                // Calculate text size based on distance from center
                val scale = 1f - (abs(x - centerX) / (width / 2f)) * 0.4f
                paint.textSize = (18.dpToPx(context) * scale).toFloat()

                // Draw value
                paint.color = if (x == centerX) Color.BLACK else Color.GRAY
                val text = "${numbers[position]} кг"
                canvas.drawText(text, x, centerY + paint.textSize/3, paint)
            }
        }
    }

    private var lastX = 0f
    private var isDragging = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                isDragging = true
                animator?.cancel()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val dx = event.x - lastX
                    scrollOffset -= dx

                    // Update selected position based on scroll
                    while (scrollOffset > itemWidth) {
                        if (selectedPosition < numbers.size - 1) {
                            selectedPosition++
                            scrollOffset -= itemWidth
                        } else {
                            scrollOffset = itemWidth.toFloat()
                        }
                    }
                    while (scrollOffset < -itemWidth) {
                        if (selectedPosition > 0) {
                            selectedPosition--
                            scrollOffset += itemWidth
                        } else {
                            scrollOffset = (-itemWidth).toFloat()
                        }
                    }

                    lastX = event.x
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