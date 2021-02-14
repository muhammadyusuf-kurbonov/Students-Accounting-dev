package uz.muhammadyusuf.kurbonov.repository

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val migration1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.beginTransaction()
        database.execSQL("CREATE TABLE T_1 AS SELECT * FROM AccountingItem;")

    }

}