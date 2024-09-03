package com.github.vitorfg8.pettracks.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun Date.updateTime(hourOfDay: Int, minute: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    calendar.set(Calendar.MINUTE, minute)
    return calendar.time
}

fun Date.toLocalDateFormat(): String {
    val calendar = Calendar.getInstance().apply {
        time = this@toLocalDateFormat
    }
    val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.getDefault())
    val pattern = (dateFormat as SimpleDateFormat).toPattern()
    val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(calendar.time)
}