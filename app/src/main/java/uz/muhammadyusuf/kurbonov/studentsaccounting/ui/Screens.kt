package uz.muhammadyusuf.kurbonov.studentsaccounting.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import uz.muhammadyusuf.kurbonov.core.states.AccountingGroupLoadStates
import uz.muhammadyusuf.kurbonov.defaultresources.EmptyPage
import uz.muhammadyusuf.kurbonov.defaultresources.LoadingPage

@Composable
fun MainScreen(listState: State<AccountingGroupLoadStates>) {
    when (listState.value) {
        AccountingGroupLoadStates.LoadingState -> LoadingPage(
            modifier = Modifier.fillMaxSize()
        )
        is AccountingGroupLoadStates.Data -> TODO()
        AccountingGroupLoadStates.EmptyList -> EmptyPage(
            modifier = Modifier.fillMaxSize()
        )
        is AccountingGroupLoadStates.Error -> TODO()
    }
}