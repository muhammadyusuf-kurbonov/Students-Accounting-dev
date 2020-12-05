package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states

sealed class DetailsCardState {
    object Opening : DetailsCardState()
    object Opened : DetailsCardState()
    object Closing : DetailsCardState()
    object Closed : DetailsCardState()
}