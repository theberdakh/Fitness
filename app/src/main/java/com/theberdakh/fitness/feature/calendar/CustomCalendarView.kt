package com.theberdakh.fitness.feature.calendar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.Calendar
import kotlin.math.min

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val cellPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    
    private var cellWidth = 0f
    private var cellHeight = 0f
    private val calendar = Calendar.getInstance()
    private val today = Calendar.getInstance()
    
    // Data structures to hold decorations
    private val coloredDates = mutableMapOf<Int, Int>() // date to color
    private val connectedDates = mutableListOf<Pair<Int, Int>>() // pairs of dates to connect
    
    init {
        textPaint.apply {
            textSize = 40f
            textAlign = Paint.Align.CENTER
            color = Color.BLACK
        }
        
        linePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = Color.BLUE
        }
        
        cellPaint.style = Paint.Style.FILL
    }
    
    fun setDateColor(date: Int, color: Int) {
        coloredDates[date] = color
        invalidate()
    }
    
    fun connectDates(startDate: Int, endDate: Int) {
        connectedDates.add(Pair(startDate, endDate))
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cellWidth = w / 7f
        cellHeight = h / 6f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Draw grid and dates
        var dayOfMonth = 1
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        
        for (row in 0..5) {
            for (col in 0..6) {
                val position = row * 7 + col
                if (position >= firstDayOfWeek && dayOfMonth <= daysInMonth) {
                    val cellX = col * cellWidth
                    val cellY = row * cellHeight
                    
                    // Draw background color if date is decorated
                    coloredDates[dayOfMonth]?.let { color ->
                        cellPaint.color = color
                        canvas.drawRect(
                            cellX + 2,
                            cellY + 2,
                            cellX + cellWidth - 2,
                            cellY + cellHeight - 2,
                            cellPaint
                        )
                    }
                    
                    // Draw date number
                    val textX = cellX + cellWidth / 2
                    val textY = cellY + cellHeight / 2 + textPaint.textSize / 3
                    canvas.drawText(dayOfMonth.toString(), textX, textY, textPaint)
                    
                    dayOfMonth++
                }
            }
        }
        
        // Draw connecting lines
        for ((startDate, endDate) in connectedDates) {
            drawConnection(canvas, startDate, endDate)
        }
    }
    
    private fun drawConnection(canvas: Canvas, startDate: Int, endDate: Int) {
        val startPos = getDatePosition(startDate)
        val endPos = getDatePosition(endDate)
        
        if (startPos != null && endPos != null) {
            val startX = (startPos.second * cellWidth) + cellWidth / 2
            val startY = (startPos.first * cellHeight) + cellHeight / 2
            val endX = (endPos.second * cellWidth) + cellWidth / 2
            val endY = (endPos.first * cellHeight) + cellHeight / 2
            
            canvas.drawLine(startX, startY, endX, endY, linePaint)
        }
    }
    
    private fun getDatePosition(date: Int): Pair<Int, Int>? {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        val position = date + firstDayOfWeek - 1
        
        val row = position / 7
        val col = position % 7
        
        return if (date <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            Pair(row, col)
        } else null
    }
}