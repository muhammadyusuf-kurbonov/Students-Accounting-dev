package uz.muhammadyusuf.kurbonov.core.repos

import android.content.Context
import uz.muhammadyusuf.kurbonov.repository.AppDatabase
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

internal class RepositoryImpl : Repository {
    private lateinit var database: AppDatabase


    override fun initDatabase(context: Context) {
        database = AppDatabase.getInstance(context)
    }

    override suspend fun insertNewItem(item: AccountingItem) =
        database.getAccountingObject().insertNewItem(item)

    override suspend fun updateItem(item: AccountingItem) =
        database.getAccountingObject().updateItem(item)

    override suspend fun getFirstPart(): List<AccountingItem> =
        database.getAccountingObject().loadPage(0, 30)

    override suspend fun getItem(id: Int): AccountingItem =
        database.getAccountingObject().getItem(id)

    override suspend fun calculateSum(): Double =
        database.getAccountingObject().calculateSum().toDouble()
}
