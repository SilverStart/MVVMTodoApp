package com.silverstar.base.ui.util

import android.content.Context
import android.util.TypedValue

fun Context.getPxFromDp(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        .toInt()
}