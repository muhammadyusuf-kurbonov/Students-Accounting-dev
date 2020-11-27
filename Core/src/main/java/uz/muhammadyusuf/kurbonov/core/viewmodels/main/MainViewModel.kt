package uz.muhammadyusuf.kurbonov.core.viewmodels.main

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import uz.muhammadyusuf.kurbonov.core.repos.Repository
import uz.muhammadyusuf.kurbonov.core.repos.RepositoryImpl
import uz.muhammadyusuf.kurbonov.core.states.AccountingGroupLoadStates

class MainViewModel : ViewModel() {
    private lateinit var repository: Repository
    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(job + Dispatchers.IO)
    private var isStarted: Boolean = false

    private val allData = MutableStateFlow<AccountingGroupLoadStates>(
        AccountingGroupLoadStates.LoadingState
    )

    fun initRepository(context: Context) {
        repository = RepositoryImpl()
        repository.initDatabase(context)
    }

    fun getAllData(): StateFlow<AccountingGroupLoadStates> {
        if (!isStarted) {
            scope.launch {
                repository.listenAllData().collect {
                    delay(5000)
                    if (it.isEmpty())
                        allData.value = AccountingGroupLoadStates.EmptyList
                    else
                        allData.value = AccountingGroupLoadStates.Data(it)
                }
            }
        }
        return allData
    }
}