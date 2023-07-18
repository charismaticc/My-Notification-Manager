package com.sharipov.mynotificationmanager.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun dateConverter(date: String):Long? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return if (date != "") {
        val fromDateLocalDate = LocalDate.parse(date, formatter)
        val fromDateTime = fromDateLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        fromDateTime.toEpochMilli()
    } else{
        null
    }
}