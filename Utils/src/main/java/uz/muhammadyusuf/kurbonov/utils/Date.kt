package uz.muhammadyusuf.kurbonov.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun Long.formatAsDate(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))

fun Long.formatAsDate(): String = formatAsDate("dd MMM yyyy")

fun String.reformatDate(oldFormat: String, newFormat: String): String {
    val date = SimpleDateFormat(oldFormat, Locale.getDefault()).parse(this)

    val result = date?.time?.formatAsDate(newFormat)
        ?: throw IllegalArgumentException("Wrong pattern. Check it")
    Log.d("Dates", "$this => $result")
    return result
}

suspend fun openDatePickerDialog(context: Context) = suspendCoroutine<Long> {
    var date = System.currentTimeMillis()
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
        date = calendar.timeInMillis
        it.resume(date)
    }
    DatePickerDialog(
        context,
        listener,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    ).show()
}