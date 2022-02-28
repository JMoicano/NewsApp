package dev.jmoicano.newsapp.extensions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun Instant.formatToDate(locale: Locale = Locale.getDefault(), zoneId: ZoneId = ZoneId.systemDefault()): String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        .withLocale(locale)
        .withZone(zoneId)
    return formatter.format(this)
}