package com.vanthien113.tiannote.shared.utilities

import android.graphics.Color

object ColorUtils {
    fun getColorFromHex(hex: String): Int {
        return when {
            hex.contains("#") -> Color.parseColor(hex)
            else -> Color.parseColor("#$hex")
        }
    }
}