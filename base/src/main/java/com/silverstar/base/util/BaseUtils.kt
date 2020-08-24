package com.silverstar.base.util

import java.util.*

object BaseUtils {

    fun isKorean(): Boolean {
        val locale = Locale.getDefault()

        if (locale == Locale.KOREA || locale == Locale.KOREAN) {
            return true
        }

        return false
    }
}