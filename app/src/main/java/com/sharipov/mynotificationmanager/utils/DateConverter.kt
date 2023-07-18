package com.sharipov.mynotificationmanager.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun dateConverter(type: String, date: String):Long? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return if (date != "") {
        val fromDateLocalDate: LocalDate
        val fromDateTime: Instant
        if(type == "to"){
            fromDateLocalDate = LocalDate.parse(date, formatter).plusDays(1)
            fromDateTime = fromDateLocalDate.atStartOfDay(ZoneId.systemDefault()).plusSeconds(-1).toInstant()
        }else {
            fromDateLocalDate = LocalDate.parse(date, formatter)
            fromDateTime = fromDateLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        }
        fromDateTime.toEpochMilli()
    } else{
        null
    }
}