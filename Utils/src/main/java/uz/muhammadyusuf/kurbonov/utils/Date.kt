package uz.muhammadyusuf.kurbonov.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatAsDate(pattern: String) =
    SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))

fun Long.formatAsDate() = formatAsDate("dd MMM yyyy")