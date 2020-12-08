package uz.muhammadyusuf.kurbonov.studentsaccounting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.*
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states.DetailsCardState
import uz.muhammadyusuf.kurbonov.utils.formatAsDate
import uz.muhammadyusuf.kurbonov.utils.openDatePickerDialog
import java.text.NumberFormat
import java.util.*

@ExperimentalCoroutinesApi
@Composable
fun MainScreen(
    listState: androidx.compose.runtime.State<AccountingGroupLoadStates>,
    onNewPartRequest: () -> Unit = {},
    onItemClick: (item: AccountingItem) -> Unit = {}
) {
    when (listState.value) {

        // ===============================  LOADING...  =======================================
        AccountingGroupLoadStates.LoadingState -> LoadingPage(
            modifier = Modifier.fillMaxSize()
        )

        // ================================  DATA  ============================================
        is AccountingGroupLoadStates.Data -> {

            Surface(color = MaterialTheme.colors.surface) {
                Box {
                    val items = (listState.value as AccountingGroupLoadStates.Data).data
                    LazyColumn {

                        item {
                            val model = viewModel<MainViewModel>()
                            model.initRepository(AmbientContext.current)

                            val sumState = model.getTotalSum().collectAsState()

                            BalanceReport(
                                modifier = Modifier.fillMaxWidth(),
                                sumState = sumState
                            )
                        }

                        itemsIndexed(
                            items = items
                        ) { index, item ->
                            if (items.size - index - 1 < 10)
                                onNewPartRequest()
                            Padding(paddingValues = PaddingValues(defaultMargin()))
                            {
                                MainListItem(item = item, onClick = {
                                    onItemClick(it)
                                })
                            }
                        }
                    }
                }
            }
        }


        // ================================  EMPTY  ===========================================
        AccountingGroupLoadStates.EmptyList -> EmptyPage(
            modifier = Modifier.fillMaxSize()
        )


        // ================================  ERROR  ============================================
        is AccountingGroupLoadStates.Error -> ErrorPage()
    }
}

@Composable
fun DetailsScreen(
    item: AccountingItem?,
    onEdit: (AccountingItem) -> Unit = {},
    onDelete: (AccountingItem) -> Unit = {},
    onClosed: () -> Unit = {}
) {

    val showState = remember { mutableStateOf<DetailsCardState>(DetailsCardState.Opening) }


    DetailsLayout(
        showState = showState,
        onDismissRequest = {
            showState.value = DetailsCardState.Closing
        },
        onClosed = onClosed
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(
                    id = R.string.details
                ),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(defaultPadding())
            )
            TextButton(onClick = {
                showState.value = DetailsCardState.Closing
            }) {
                Text(text = "x", color = MaterialTheme.colors.onBackground)
            }
        }
        Text(
            text = item?.itemDescription ?: stringResource(id = R.string.loading_label),
            modifier = Modifier.fillMaxWidth().padding(defaultMargin()),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (item != null) "Date ${item.date.prettifyDate()}"
            else stringResource(id = R.string.loading_label),
            modifier = Modifier.fillMaxWidth().padding(defaultMargin())
        )

        Text(
            text = stringResource(uz.muhammadyusuf.kurbonov.studentsaccounting.R.string.sum_prefix) + item?.totalSum,
            modifier = Modifier.fillMaxWidth().padding(defaultMargin()),
            style = MaterialTheme.typography.subtitle1
        )

        if (item != null) {
            Button(onClick = {
                showState.value = DetailsCardState.Closing
                onEdit(item)
            }, modifier = Modifier.padding(defaultPadding())) {
                Text(text = "Edit", style = MaterialTheme.typography.button)
            }

            Button(onClick = {
                onDelete(item)
            }, modifier = Modifier.padding(defaultPadding())) {
                Text(text = "Delete", style = MaterialTheme.typography.button)
            }
        }
    }
}


@Composable
fun AddEditScreen(item: AccountingItem? = null, onDismissRequest: () -> Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.padding(defaultPadding()),
            shape = RoundedCornerShape(8.dp)
        ) {

            val date = remember { mutableStateOf(System.currentTimeMillis().formatAsDate()) }
            val description = remember { mutableStateOf("") }
            val sum = remember { mutableStateOf(0.0) }
            val sendingState = remember { mutableStateOf(false) }

            val itemType = remember { mutableStateOf("in") }

            if (item != null) {
                description.value = item.itemDescription
                sum.value = kotlin.math.abs(item.totalSum)
                itemType.value = if (item.totalSum > 0) "in" else "out"
                date.value = item.date.prettifyDate()
            }


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
                        label = { Text(text = stringResource(uz.muhammadyusuf.kurbonov.studentsaccounting.R.string.date)) },
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
                        },
                        modifier = Modifier.fillMaxWidth().padding(defaultPadding())
                    )

                    OutlinedTextField(
                        value = description.value,
                        onValueChange = {
                            description.value = it
                        },
                        label = { Text(text = stringResource(id = R.string.description)) },
                        modifier = Modifier.fillMaxWidth().padding(defaultPadding())
                    )

                    val formatter = NumberFormat.getCurrencyInstance().apply {
                        maximumFractionDigits = 0
                        currency = Currency.getInstance("USD")
                    }
                    OutlinedTextField(
                        value = formatter.format(sum.value),
                        onValueChange = {
                            sum.value = formatter.parse(it)?.toDouble() ?: 0.0
                        },
                        label = { Text(text = stringResource(id = R.string.description)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().padding(defaultPadding())
                    )

                    val model = viewModel<AddEditViewModel>()
                    model.initRepository(AmbientContext.current)

                    Button(
                        modifier = Modifier.padding(defaultPadding()),
                        onClick = {
                            if (!sendingState.value) {
                                model.submit(
                                    item = AccountingItem(
                                        id = item?.id ?: 0,
                                        itemDescription = description.value,
                                        date = date.value.dateToSQLFormat(),
                                        totalSum = sum.value * if (itemType.value == "in") 1.0 else -1.0
                                    )
                                ) {
                                    onDismissRequest()
                                }
                            }
                        }) {
                        Text(
                            text = if (sendingState.value) stringResource(uz.muhammadyusuf.kurbonov.studentsaccounting.R.string.sending_label)
                            else stringResource(uz.muhammadyusuf.kurbonov.studentsaccounting.R.string.submit_label)
                        )
                    }
                }
            }

        }
    }
}
