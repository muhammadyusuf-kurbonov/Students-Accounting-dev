package uz.muhammadyusuf.kurbonov.core.repos

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup

internal interface Repository {
    fun getSingleAllData(): Flow<List<AccountingGroup>>

    fun initDatabase(context: Context)

    fun listenAllData(): Flow<List<AccountingGroup>>

    suspend fun getSingleGroup(id: Int): AccountingGroup

    suspend fun insertGroup(accountingGroup: AccountingGroup)

    suspend fun updateGroup(accountingGroup: AccountingGroup)
}