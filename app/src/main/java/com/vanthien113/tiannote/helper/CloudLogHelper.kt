package com.vanthien113.tiannote.helper

import android.os.Build

/**
 * Used for log into Firebase FireStore
 */

//TODO Implement Firebase later
class CloudLogHelper {
    companion object {
        private const val PATH_LOG = "logs"
        private const val PATH_ERROR = "errors"
        private const val STRING_ERROR = "error"
        private const val STRING_MESSAGE = "message"
        private const val STRING_DEVICE = "device"
        private const val STRING_TIME = "time"
        private const val STRING_STACK_TRADE = "stack_trade"

        private lateinit var helper: CloudLogHelper

        fun getInstance(): CloudLogHelper {
            if (!::helper.isInitialized) {
                helper = CloudLogHelper()
            }
            return helper
        }
    }

//    private val db = Firebase.firestore

    fun logMessage(msg: String, stackTrade: String? = null) {
        storeLog(
            hashMapOf(
                STRING_MESSAGE to msg,
                STRING_STACK_TRADE to stackTrade
            )
        )
    }

    /**
     * Log error to FireStore
     * Format: errors/2022_09_21_05_08_07_575/{error}
     */

    fun logError(msg: Throwable?) {
//        val (_, currentTime) = getCurrentDateTime()
//        db.collection(PATH_ERROR)
//            .document(currentTime)
//            .set(
//                hashMapOf(
//                    STRING_DEVICE to getDeviceId(),
//                    STRING_TIME to System.currentTimeMillis(),
//                    STRING_ERROR to msg?.message,
//                    STRING_STACK_TRADE to msg?.stackTraceToString()
//                )
//            )
//            .addOnFailureListener { e ->
//                LogUtils.logE("Push cloud log error: ${e.message}")
//            }
    }

    fun logMessages(msg: HashMap<String, Any?>) {
        storeLog(msg)
    }

    /**
     * Log error to FireStore
     * Format: logs/Redmi_Note_7_QKQ1.190910.002/2022_09_21/2022_09_21_05_08_45_865/{log}
     */
    private fun storeLog(msg: HashMap<String, Any?>) {
//        val (currentDate, currentTime) = getCurrentDateTime()

//        db.collection(PATH_LOG)
//            .document(getDeviceId())
//            .collection(currentDate)
//            .document(currentTime)
//            .set(msg)
//            .addOnFailureListener { e ->
//                LogUtils.logE("Push cloud log error: ${e.message}")
//            }
    }

    //TODO Implement Firebase later
//    private fun getCurrentDateTime(): Pair<String, String> {
//        val currentCalendar = DateTimeUtils.getUTCCalendar()
//        val currentDate = currentCalendar.toFormattedString(DateTimeUtils.FORMAT_yyyyMMdd_UnderScore)
//        val currentTime = currentCalendar.toFormattedString(DateTimeUtils.FORMAT_yyyyMMddhhmmss_UnderScore)
//        return currentDate to currentTime
//    }

    /**
     * Get device id replaced by number and character only
     * Format: Redmi_Note_7_QKQ1.190910.002
     */
    private fun getDeviceId(): String {
        return Build.MODEL.plus(" ").plus(Build.ID).replace(Regex("/[^a-z0-9]|\\s+|\\r?\\n|\\r/gmi"), "_")
    }
}
