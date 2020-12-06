package uz.muhammadyusuf.kurbonov.core.viewmodels.add_edit

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.core.repos.Repository
import uz.muhammadyusuf.kurbonov.core.repos.RepositoryImpl
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

class AddEditViewModel : ViewModel() {
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

    fun submit(item: AccountingItem, onFinish: () -> Unit = {}) {
        viewModelScope.launch {
            Log.d("TAG", "submit: item is $item")
            if (item.id == 0)
                repository.insertNewItem(item)
            else if (item.id > 0)
                repository.updateItem(item)
            onFinish()
        }
    }
}