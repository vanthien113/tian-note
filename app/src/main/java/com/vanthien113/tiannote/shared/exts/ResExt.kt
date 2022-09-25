package com.vanthien113.tiannote.shared.exts

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun Int.getColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

fun Int.getDrawable(context: Context): Drawable? {
    return ContextCompat.getDrawable(context, this)
}