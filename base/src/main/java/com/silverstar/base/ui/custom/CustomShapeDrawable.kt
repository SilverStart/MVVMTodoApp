package com.silverstar.base.ui.custom

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import androidx.annotation.ColorInt

class CustomShapeDrawable(
    s: Shape,
    @ColorInt fillColor: Int,
    @ColorInt strokeColor: Int? = null,
    dashPhase: Int? = null,
    private val strokeWidth: Float = 1f
) : ShapeDrawable(s) {

    private val fillPaint: Paint = Paint(paint)
    private var strokePaint: Paint? = null

    init {
        fillPaint.color = fillColor
        if (strokeColor != null) {
            strokePaint = Paint(fillPaint)
            strokePaint!!.style = Paint.Style.STROKE
            strokePaint!!.strokeWidth = strokeWidth
            strokePaint!!.color = strokeColor
            if (dashPhase != null) {
                strokePaint!!.pathEffect =
                    DashPathEffect(floatArrayOf(5f, 5f), dashPhase.toFloat())
            }
        }
    }

    override fun onDraw(shape: Shape, canvas: Canvas, paint: Paint) {
        shape.resize(canvas.clipBounds.right.toFloat(), canvas.clipBounds.bottom.toFloat())
        shape.draw(canvas, fillPaint)

        if (strokePaint == null) return

        val halfStrokeWidth = strokeWidth / 2

        val m = Matrix()
        m.setRectToRect(
            RectF(0f, 0f, canvas.clipBounds.right.toFloat(), canvas.clipBounds.bottom.toFloat()),
            RectF(
                halfStrokeWidth,
                halfStrokeWidth,
                canvas.clipBounds.right - halfStrokeWidth,
                canvas.clipBounds.bottom - halfStrokeWidth
            ),
            Matrix.ScaleToFit.FILL
        )

        canvas.concat(m)

        shape.draw(canvas, strokePaint)
    }
}