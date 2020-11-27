package uz.muhammadyusuf.kurbonov.core.repos

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.muhammadyusuf.kurbonov.repository.AppDatabase
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup

internal class RepositoryImpl : Repository {
    private lateinit var database: AppDatabase
    override fun getSingleAllData(): Flow<List<AccountingGroup>> = flow {
        emit(database.getAccountingObject().getAllGroups())
    }


    override fun initDatabase(context: Context) {
        database = AppDatabase.getInstance(context)
    }

    override fun listenAllData(): Flow<List<AccountingGroup>> =
        database.getAccountingObject().listenAllGroups()

    override suspend fun getSingleGroup(id: Int): AccountingGroup =
        database.getAccountingObject().getGroup(id)

    override suspend fun insertGroup(accountingGroup: AccountingGroup) =
        database.getAccountingObject().insertGroup(accountingGroup)

    override suspend fun updateGroup(accountingGroup: AccountingGroup) =
        database.getAccountingObject().updateGroup(accountingGroup)
}