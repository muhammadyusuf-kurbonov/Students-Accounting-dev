package uz.muhammadyusuf.kurbonov.core.viewmodels.main

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup

interface MainRepository {
    fun getSingleAllData(): Flow<List<AccountingGroup>>

    fun initDatabase(context: Context)

    fun listenAllData(): Flow<List<AccountingGroup>>
}