package uz.muhammadyusuf.kurbonov.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.muhammadyusuf.kurbonov.repository.dao.AccountingDao
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

@Database(entities = [AccountingItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context, debug: Boolean = false): AppDatabase {
            if (instance == null) {
                val builder =
                    if (!debug) Room.databaseBuilder(context, AppDatabase::class.java, "main.db")
                    else Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                instance = builder.build()
            }
            return instance!!
        }
    }

    abstract fun getAccountingObject(): AccountingDao
}