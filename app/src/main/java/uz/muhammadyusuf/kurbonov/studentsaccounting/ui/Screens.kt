package uz.muhammadyusuf.kurbonov.studentsaccounting.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.tapGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.core.states.AccountingGroupLoadStates
import uz.muhammadyusuf.kurbonov.core.viewmodels.add_edit.AddEditViewModel
import uz.muhammadyusuf.kurbonov.defaultresources.EmptyPage
import uz.muhammadyusuf.kurbonov.defaultresources.ErrorPage
import uz.muhammadyusuf.kurbonov.defaultresources.LoadingPage
import uz.muhammadyusuf.kurbonov.defaultresources.R
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroupItem
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.AddEditLayout
import uz.muhammadyusuf.kurbonov.utils.formatAsDate
import uz.muhammadyusuf.kurbonov.utils.openDatePickerDialog

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
        is AccountingGroupLoadStates.Error -> ErrorPage()
    }
}

@Composable
fun AddEditScreen(showState: State<Boolean>, id: Int = -1, addEditViewModel: AddEditViewModel) {
    val item = AccountingGroupItem()

    var date = remember { mutableStateOf(System.currentTimeMillis()) }

    AddEditLayout(showState = showState) {
        Text(
            text = stringResource(
                id = if (id == -1) R.string.add_label
                else R.string.edit_label
            ),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(defaultPadding())
        )

        OutlinedTextField(value = item.description, onValueChange = { item.description = it })

        val context = ContextAmbient.current

        Text(
            text = date.value.formatAsDate(),
            modifier = Modifier.tapGestureFilter {
                addEditViewModel.viewModelScope.launch {
                    date.value = openDatePickerDialog(context)
                }
            }.border(1.dp, Color.Black.copy(alpha = 0.42f), RoundedCornerShape(4.dp))
                .padding(defaultMargin())
        )

        Button(onClick = { addEditViewModel.submit(item) }) {
            Text(
                text = stringResource(
                    id = if (id == -1) R.string.add_label
                    else R.string.edit_label
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewAddEdit() {
    AddEditScreen(derivedStateOf { true }, addEditViewModel = AddEditViewModel())
}

