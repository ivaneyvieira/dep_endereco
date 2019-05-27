package br.com.astrosoft.model.framework.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")
val LOCALDATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

fun LocalDateTime?.toDate(): Date? {
  if (this == null) return null
  val instant = this.atZone(ZoneId.systemDefault())?.toInstant()
  return Date.from(instant)
}

fun LocalDateTime?.toTimeStamp(): Timestamp? {
  if (this == null) return null
  val instant = this.atZone(ZoneId.systemDefault())?.toInstant()
  return Timestamp.from(instant)
}

fun LocalDate?.toDate(): Date? {
  if (this == null) return null
  val instant = this.atStartOfDay()?.atZone(ZoneId.systemDefault())?.toInstant()
  return Date.from(instant)
}

fun LocalTime?.toDate(): Date? {
  if (this == null) return null
  val date = LocalDate.now()
  val year = date.year
  val month = date.month
  val dayOfMonth = date.dayOfMonth
  val instant = this.atDate(LocalDate.of(year, month, dayOfMonth))?.atZone(ZoneId.systemDefault())?.toInstant()
  return Date.from(instant)
}

fun Date?.toLocalDateTime(): LocalDateTime? {
  if (this == null) return null
  val instant = Instant.ofEpochMilli(this.time)
  return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

fun Date?.toLocalDate(): LocalDate? {
  if (this == null) return null
  val instant = Instant.ofEpochMilli(this.time)
  val zone = ZoneId.systemDefault()
  val zdt = instant.atZone(zone)
  return zdt.toLocalDate()
}

fun LocalDateTime?.format(): String? {
  if (this == null) return null
  return LOCALDATE_FORMAT.format(this)
}

fun Date?.format(): String? {
  if (this == null) return null
  return DATE_FORMAT.format(this)
}