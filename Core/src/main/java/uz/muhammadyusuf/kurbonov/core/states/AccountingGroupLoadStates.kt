package uz.muhammadyusuf.kurbonov.core.states

import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup

sealed class AccountingGroupLoadStates {
    object LoadingState : AccountingGroupLoadStates()
    class Data(val data: List<AccountingGroup>) : AccountingGroupLoadStates()
    object EmptyList : AccountingGroupLoadStates()
    class Error(val exception: Exception) : AccountingGroupLoadStates()
}

