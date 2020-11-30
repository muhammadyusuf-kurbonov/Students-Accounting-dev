package uz.muhammadyusuf.kurbonov.core.viewmodels.add_edit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.core.repos.Repository
import uz.muhammadyusuf.kurbonov.core.repos.RepositoryImpl
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroupItem
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

class AddEditViewModel(private val id: Int = -1) : ViewModel() {
    private lateinit var repository: Repository

    private val itemsData = MutableStateFlow<List<AccountingItem>>(
        emptyList()
    )

    fun initRepository(context: Context) {
        repository = RepositoryImpl()
        repository.initDatabase(context)
    }

    fun getItems(): StateFlow<List<AccountingItem>> {
        return itemsData
    }

    fun addItem(accountingItem: AccountingItem) {
        itemsData.value = itemsData.value.toMutableList().apply { add(accountingItem) }
    }

    fun removeItem(accountingItem: AccountingItem) {
        itemsData.value = itemsData.value.toMutableList().apply { remove(accountingItem) }
    }

    fun submit(accountingGroupItem: AccountingGroupItem) {
        val accountingGroup = AccountingGroup(
            accountingGroupItem,
            itemsData.value
        )

        if (id > -1) {
            viewModelScope.launch { repository.updateGroup(accountingGroup) }
        } else {
            viewModelScope.launch { repository.insertGroup(accountingGroup) }
        }

    }
}