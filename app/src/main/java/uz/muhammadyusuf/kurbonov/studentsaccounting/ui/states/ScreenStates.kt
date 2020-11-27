package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states

sealed class ScreenStates {
    object MainScreenState : ScreenStates()
    class AddEditScreenState(@Suppress("unused") private val id: Int = -1) : ScreenStates()
}