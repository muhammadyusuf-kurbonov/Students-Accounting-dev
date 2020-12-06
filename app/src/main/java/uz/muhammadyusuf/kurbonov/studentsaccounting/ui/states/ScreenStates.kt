package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states

import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem

sealed class ScreenStates {
    object MainScreenState : ScreenStates()
    class AddEditScreenState(val item: AccountingItem? = null) : ScreenStates()
    class DetailsScreenState(val id: Int) : ScreenStates()
}