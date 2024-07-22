package com.himawan.weighbridge.common

import android.os.Build
import com.himawan.weighbridge.data.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {

    fun getCurrentDate(pattern: String = "yyyy-MM-dd"): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
            current.format(formatter)
        } else {
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(pattern)
            formatter.format(calendar.time)
        }
    }

    fun getCurrentTime(pattern: String = "HH:mm:ss"): String {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
            current.format(formatter)
        } else {
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(pattern)
            formatter.format(calendar.time)
        }
    }

}

fun Long.toServerDateTimeFormat(pattern: String = DateFormat.PREVIEW_DATETIME_FORMAT): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(Date(this))
}

fun String.convertDateFormat(currentPattern: String = DateFormat.DATE_FORMAT, expectedPattern: String = DateFormat.PREVIEW_DATE_FORMAT): String {

    val inputFormat = SimpleDateFormat(currentPattern)
    val outputFormat = SimpleDateFormat(expectedPattern)

    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}