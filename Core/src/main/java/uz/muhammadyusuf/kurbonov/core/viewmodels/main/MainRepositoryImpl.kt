package uz.muhammadyusuf.kurbonov.core.viewmodels.main

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.muhammadyusuf.kurbonov.repository.AppDatabase
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup

class MainRepositoryImpl : MainRepository {
    private lateinit var database: AppDatabase
    override fun getSingleAllData(): Flow<List<AccountingGroup>> = flow {
        emit(database.getAccountingObject().getAllGroups())
    }


    override fun initDatabase(context: Context) {
        database = AppDatabase.getInstance(context)
    }

    override fun listenAllData(): Flow<List<AccountingGroup>> = database.getAccountingObject().listenAllGroups()
}