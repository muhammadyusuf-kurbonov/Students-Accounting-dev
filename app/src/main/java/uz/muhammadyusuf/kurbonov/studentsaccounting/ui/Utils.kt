package uz.muhammadyusuf.kurbonov.studentsaccounting.ui

import uz.muhammadyusuf.kurbonov.utils.reformatDate

fun String.dateToSQLFormat() = reformatDate("dd MMM yyyy", "yyyy-MM-DD")

fun String.prettifyDate() = reformatDate("yyyy-MM-DD", "dd MMM YYYY")