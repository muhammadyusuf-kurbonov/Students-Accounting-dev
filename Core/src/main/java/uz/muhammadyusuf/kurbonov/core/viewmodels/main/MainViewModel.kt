package uz.muhammadyusuf.kurbonov.core.viewmodels.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.core.repos.Repository
import uz.muhammadyusuf.kurbonov.core.repos.RepositoryImpl
import uz.muhammadyusuf.kurbonov.core.states.AccountingGroupLoadStates

class MainViewModel : ViewModel() {
    private lateinit var repository: Repository

    private val allData = MutableStateFlow<AccountingGroupLoadStates>(
        AccountingGroupLoadStates.LoadingState
    )
    private val totalSum = MutableStateFlow<Int?>(
        null
    )

    fun initRepository(context: Context) {
        repository = RepositoryImpl()
        repository.initDatabase(context)
    }

    fun getAllData(): StateFlow<AccountingGroupLoadStates> {

        viewModelScope.launch {
            val firstPart = repository.getFirstPart()
            if (firstPart.isEmpty())
                allData.value = AccountingGroupLoadStates.EmptyList
            else
                allData.value = AccountingGroupLoadStates.Data(firstPart)
        }
        return allData
    }

    fun getTotalSum(): StateFlow<Int?> {

        viewModelScope.launch {
            val sum = repository.calculateSum()
            totalSum.value = sum
            Log.d("SUM", "getTotalSum: sum is $sum")
        }

        return totalSum
    }

}