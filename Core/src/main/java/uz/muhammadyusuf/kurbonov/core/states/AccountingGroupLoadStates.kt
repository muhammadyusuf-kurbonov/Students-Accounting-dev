package uz.muhammadyusuf.kurbonov.core.states

import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

sealed class AccountingGroupLoadStates {
    object LoadingState : AccountingGroupLoadStates()
    class Data(val data: List<AccountingItem>) : AccountingGroupLoadStates()
    object EmptyList : AccountingGroupLoadStates()
    class Error(val exception: Exception) : AccountingGroupLoadStates()
}

