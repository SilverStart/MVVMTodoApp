package com.silverstar.base.ui.util

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silverstar.base.ui.adapter.DataBindingAdapter
import com.silverstar.base.ui.custom.CustomShapeDrawable

@BindingAdapter(
    "bind:backgroundColor",
    "bind:pressedColor",
    "bind:borderColor",
    "bind:radius",
    "bind:dashPhase",
    requireAll = false
)
fun View.setBackground(
    @ColorInt backgroundColor: Int?,
    @ColorInt pressedColor: Int?,
    @ColorInt borderColor: Int?,
    radius: Int?,
    dashPhase: Int?
) {
    fun getShapeDrawable(
        @ColorInt color: Int,
        radius: Float = 10f
    ): Drawable {
        val pxRadius = context.getPxFromDp(radius).toFloat()
        val outerRadii =
            floatArrayOf(
                pxRadius,
                pxRadius,
                pxRadius,
                pxRadius,
                pxRadius,
                pxRadius,
                pxRadius,
                pxRadius
            )

        return CustomShapeDrawable(
            RoundRectShape(outerRadii, null, null),
            color,
            borderColor,
            dashPhase,
            3f
        )
    }

    fun getStateListDrawable(
        @ColorInt normalColor: Int,
        @ColorInt pressedColor: Int
    ): Drawable {
        return StateListDrawable().apply {
            val sd = getShapeDrawable(pressedColor, radius?.toFloat() ?: 0f)
            addState(intArrayOf(android.R.attr.state_pressed), sd)
            addState(intArrayOf(android.R.attr.state_focused), sd)
            addState(intArrayOf(android.R.attr.state_activated), sd)
            addState(
                intArrayOf(),
                getShapeDrawable(normalColor, radius?.toFloat() ?: 0f)
            )
        }
    }

    fun getAdaptiveRippleDrawable(
        @ColorInt normalColor: Int,
        @ColorInt pressedColor: Int
    ): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable(
                ColorStateList.valueOf(pressedColor),
                getShapeDrawable(normalColor, radius?.toFloat() ?: 0f),
                getShapeDrawable(pressedColor, radius?.toFloat() ?: 0f)
            )
        } else {
            getStateListDrawable(normalColor, pressedColor)
        }
    }

    if (backgroundColor == null) return

    if (this is ImageView) {
        setImageDrawable(getShapeDrawable(backgroundColor, radius?.toFloat() ?: 0f))
        return
    }

    background = if (pressedColor == null) {
        getShapeDrawable(backgroundColor, radius?.toFloat() ?: 0f)
    } else {
        getAdaptiveRippleDrawable(
            backgroundColor, pressedColor
        )
    }
}

@BindingAdapter("bind:imageUrl", "bind:circleCrop", requireAll = false)
fun ImageView.setImageUrl(imageUrl: String?, circleCrop: Boolean?) {
    if (imageUrl == null) return

    GlideApp.with(this).load(imageUrl).apply {
        if (circleCrop == true) circleCrop()
    }.into(this)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bind:items", "bind:spanCount", requireAll = false)
fun RecyclerView.setItems(
    items: List<Any>?,
    spanCount: Int?
) {
    if (spanCount != null && 0 < spanCount)
        (layoutManager as? GridLayoutManager)?.spanCount = spanCount
    (adapter as? DataBindingAdapter<Any>)?.submitList(items)
}
