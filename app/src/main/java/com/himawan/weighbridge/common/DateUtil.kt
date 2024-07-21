package com.himawan.weighbridge.common

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
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