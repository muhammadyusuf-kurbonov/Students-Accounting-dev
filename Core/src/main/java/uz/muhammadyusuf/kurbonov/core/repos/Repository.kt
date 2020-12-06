package uz.muhammadyusuf.kurbonov.core.repos

import android.content.Context
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

internal interface Repository {

    fun initDatabase(context: Context)

    suspend fun insertNewItem(item: AccountingItem)

    suspend fun getFirstPart(): List<AccountingItem>

    suspend fun getItem(id: Int): AccountingItem

    suspend fun calculateSum(): Double
}