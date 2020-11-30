package uz.muhammadyusuf.kurbonov.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun Long.formatAsDate(pattern: String) =
    SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))

fun Long.formatAsDate() = formatAsDate("dd MMM yyyy")


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