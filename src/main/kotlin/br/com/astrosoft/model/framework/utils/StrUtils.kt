package br.com.astrosoft.model.framework.utils

import java.text.DecimalFormat

val MONEY_FORMAT = DecimalFormat("#,##0.00")
val INT_FORMAT = DecimalFormat("0")
val QUANT_FORMAT = DecimalFormat("#,##0.####")
val formats = HashMap<String, DecimalFormat>()

fun String?.lpad(
        size: Int,
        filler: String
                ): String {
  var str = this ?: ""
  if (str.length > size) return str.substring(0, size)
  val buf = StringBuilder(str)
  while (buf.length < size) buf.insert(0, filler)
  
  str = buf.toString()
  return str
}

fun String?.rpad(
        size: Int,
        filler: String
                ): String {
  val str = this ?: ""
  if (str.length > size) return str.substring(0, size)
  val buf = StringBuilder(str)
  while (buf.length < size) buf.append(filler)
  
  return buf.toString()
}

fun String?.trimNull(): String {
  return this?.trim { it <= ' ' } ?: ""
}

fun Number.formatQuant(): String {
  return QUANT_FORMAT.format(this)
}

fun Number.formatInt(): String {
  return INT_FORMAT.format(this)
}

fun Number.formatMoeny(): String {
  return MONEY_FORMAT.format(this)
}

fun Number.format(format: String): String {
  val df = formats.getOrPut(format) { DecimalFormat(format) }
  return df.format(this)
}
