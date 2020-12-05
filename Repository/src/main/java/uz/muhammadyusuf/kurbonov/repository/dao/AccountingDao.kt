package uz.muhammadyusuf.kurbonov.repository.dao

import androidx.room.*
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

@Dao
abstract class AccountingDao {

    @Insert
    abstract suspend fun insertNewItem(item: AccountingItem)

    @Update
    abstract suspend fun updateItem(item: AccountingItem)

    @Delete
    abstract suspend fun deleteItem(item: AccountingItem)

    @Query("SELECT * FROM AccountingItem ORDER BY  date LIMIT :limit OFFSET :offset")
    abstract suspend fun loadPage(offset: Int, limit: Int = 10): List<AccountingItem>

    @Query("SELECT date FROM AccountingItem GROUP BY date  ORDER By date")
    abstract suspend fun getAllDates(): List<String>

    @Query("SELECT * FROM AccountingItem WHERE date=:date")
    abstract suspend fun getItemsInDate(date: String): List<AccountingItem>

    @Query("SELECT SUM(totalSum) FROM AccountingItem")
    abstract suspend fun calculateSum(): String

    @Query("SELECT * FROM AccountingItem WHERE id=:id")
    abstract suspend fun getItem(id: Int): AccountingItem
}