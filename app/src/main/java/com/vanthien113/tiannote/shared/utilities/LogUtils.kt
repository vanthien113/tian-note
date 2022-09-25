package com.vanthien113.tiannote.shared.utilities

import android.util.Log

object LogUtils {
    private const val TAG = "NoteApp"

    fun logE(message: String) {
        Log.e(TAG, message)
    }

    fun logE(throwable: Throwable) {
        Log.e(TAG, throwable.stackTraceToString())
    }
}
