package uz.muhammadyusuf.kurbonov.studentsaccounting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.core.states.AccountingGroupLoadStates
import uz.muhammadyusuf.kurbonov.core.viewmodels.add_edit.AddEditViewModel
import uz.muhammadyusuf.kurbonov.core.viewmodels.main.MainViewModel
import uz.muhammadyusuf.kurbonov.defaultresources.EmptyPage
import uz.muhammadyusuf.kurbonov.defaultresources.ErrorPage
import uz.muhammadyusuf.kurbonov.defaultresources.LoadingPage
import uz.muhammadyusuf.kurbonov.defaultresources.R
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.MainListItem
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.Padding
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.Spinner
import uz.muhammadyusuf.kurbonov.utils.formatAsDate
import uz.muhammadyusuf.kurbonov.utils.openDatePickerDialog
import uz.muhammadyusuf.kurbonov.utils.reformatDate

@ExperimentalCoroutinesApi
@Composable
fun MainScreen(
    listState: State<AccountingGroupLoadStates>,
    onNewPartRequest: () -> Unit = {}
) {
    when (listState.value) {
        AccountingGroupLoadStates.LoadingState -> LoadingPage(
            modifier = Modifier.fillMaxSize()
        )
        is AccountingGroupLoadStates.Data -> {

            Surface(color = MaterialTheme.colors.surface) {
                Box {
                    val items = (listState.value as AccountingGroupLoadStates.Data).data
                    LazyColumn {

                        item {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Balance",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                        .padding(defaultPadding())
                                )

                                val model = viewModel<MainViewModel>()
                                model.initRepository(AmbientContext.current)

                                Text(
                                    text = model.getTotalSum().collectAsState().value.toString(),
                                    style = MaterialTheme.typography.h4,
                                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(
                                        defaultMargin()
                                    )
                                )
                            }
                        }

                        itemsIndexed(
                            items = items
                        ) { index, item ->
                            if (items.size - index - 1 < 10)
                                onNewPartRequest()
                            Padding(paddingValues = PaddingValues(defaultMargin()))
                            {
                                MainListItem(item = item)
                            }
                        }
                    }
                }
            }


        }
        AccountingGroupLoadStates.EmptyList -> EmptyPage(
            modifier = Modifier.fillMaxSize()
        )
        is AccountingGroupLoadStates.Error -> ErrorPage()
    }
}

//@Composable
//fun DetailsScreen(
//    showState: MutableState<Boolean>,
//    item: AccountingGroup
//) {
//
//    DetailsLayout(showState = showState) {
//        Text(
//            text = stringResource(
//                id = R.string.details
//            ),
//            style = MaterialTheme.typography.h6,
//            modifier = Modifier.fillMaxWidth().padding(defaultPadding())
//        )
//
//        Text(
//            text = "Date ${item.groupItem.date.formatAsDate()}",
//            modifier = Modifier.fillMaxWidth().padding(defaultMargin())
//        )
//
//        LazyColumnFor(items = item.items) {
//
//            Row(modifier = Modifier.fillMaxWidth().padding(defaultPadding())) {
//                Text(text = it.itemName)
//                Spacer(modifier = Modifier.weight(1f))
//                Text(text = it.totalSum.toString())
//            }
//
//            Divider()
//        }
//
//        Button(onClick = { showState.value = false }) {
//            Text(text = stringResource(id = R.string.close))
//        }
//    }
//}
//
//

@Composable
fun AddEditScreen(onDismissRequest: () -> Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.padding(defaultPadding()),
            shape = RoundedCornerShape(8.dp)
        ) {

            val date = remember { mutableStateOf(System.currentTimeMillis().formatAsDate()) }
            val description = remember { mutableStateOf("") }
            val sum = remember { mutableStateOf(0) }
            val sendingState = remember { mutableStateOf(false) }

            val itemType = remember { mutableStateOf("in") }

            Padding(paddingValues = PaddingValues(defaultMargin())) {
                Column {

                    Text(
                        text = stringResource(id = R.string.add_label),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h5
                    )

                    Spinner(
                        modifier = Modifier.fillMaxWidth().padding(defaultPadding()),
                        listOf("Income", "Outcome")
                    ) {
                        if (it == "Income")
                            itemType.value = "in"
                        else
                            itemType.value = "out"
                    }

                    OutlinedTextField(
                        value = date.value,
                        onValueChange = {},
                        label = { Text(text = "Date") },
                        leadingIcon = {
                            val context = AmbientContext.current
                            Image(
                                modifier = Modifier.clickable(onClick = {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        date.value = openDatePickerDialog(context).formatAsDate()
                                    }
                                }),
                                imageVector = vectorResource(
                                    id =
                                    uz.muhammadyusuf.kurbonov.studentsaccounting.R.drawable.ic_baseline_calendar_today_24
                                ),
                                colorFilter = ColorFilter(
                                    MaterialTheme.colors.onBackground,
                                    BlendMode.SrcAtop
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { description.value = it },
                        label = { Text(text = stringResource(id = R.string.description)) }
                    )

                    OutlinedTextField(
                        value = sum.value.toString(),
                        onValueChange = { sum.value = if (it.isEmpty()) 0 else it.toInt() },
                        label = { Text(text = stringResource(id = R.string.description)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    val model = viewModel<AddEditViewModel>()
                    model.initRepository(AmbientContext.current)

                    Button(
                        modifier = Modifier.padding(defaultPadding()),
                        onClick = {
                            if (!sendingState.value) {
                                model.submit(
                                    item = AccountingItem(
                                        itemDescription = description.value,
                                        date = date.value.reformatDate("dd MMM yyyy", "yyyy-MM-DD"),
                                        totalSum = sum.value * if (itemType.value == "in") 1 else -1
                                    )
                                ) {
                                    onDismissRequest()
                                }
                            }
                        }) {
                        Text(text = if (sendingState.value) "Sending ..." else "Submit")
                    }
                }
            }

        }
    }
}
