package uz.muhammadyusuf.kurbonov.repository.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroupItem
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

@Dao
abstract class AccountingDao {
    @Insert
    protected abstract suspend fun insertGroupItem(item: AccountingGroupItem)

    @Insert
    protected abstract suspend fun insertItem(item: AccountingItem)

    @Transaction
    open suspend fun insertGroup(group: AccountingGroup) {
        insertGroupItem(group.groupItem)
        group.items.forEach {
            insertItem(it)
        }
    }

    @Query("SELECT * FROM AccountingGroupItem")
    abstract suspend fun getAllGroups(): List<AccountingGroup>


    @Query("SELECT * FROM AccountingGroupItem")
    abstract fun listenAllGroups(): Flow<List<AccountingGroup>>

    @Update
    protected abstract suspend fun updateGroupItem(item: AccountingGroupItem)

    @Update
    protected abstract suspend fun updateItem(item: AccountingItem)


    @Transaction
    open suspend fun updateGroup(group: AccountingGroup) {
        updateGroupItem(group.groupItem)
        group.items.forEach {
            updateItem(it.copy(groupId = group.groupItem.id))
        }
    }

    @Query("SELECT * FROM AccountingGroupItem WHERE id = :id")
    abstract suspend fun getGroup(id: Int): AccountingGroup
}