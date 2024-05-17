package com.github.vitorfg8.pettracks.utils

import java.util.Calendar
import java.util.Date


fun Date.updateTime(hourOfDay: Int, minute: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    calendar.set(Calendar.MINUTE, minute)
    return calendar.time
}