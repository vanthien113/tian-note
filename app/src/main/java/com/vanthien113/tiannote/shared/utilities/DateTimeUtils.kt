package com.vanthien113.tiannote.shared.utilities

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    const val FORMAT_hhmmaa = "hh:mm aa"
    const val FORMAT_ddMMyyyyhhmmaa = "dd/MM/yyyy, hh:mm aa"

    private fun convertMillisToDate(time: Long): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.time
    }

    fun getDateFromTimeStamp(time: Long, dateFormat: String): String {
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        return simpleDateFormat.format(convertMillisToDate(time))
    }

    fun removeTimeInDate(timeStamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}