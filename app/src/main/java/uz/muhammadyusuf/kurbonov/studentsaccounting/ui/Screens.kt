package uz.muhammadyusuf.kurbonov.studentsaccounting.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.muhammadyusuf.kurbonov.core.states.AccountingGroupLoadStates
import uz.muhammadyusuf.kurbonov.core.viewmodels.add_edit.AddEditViewModel
import uz.muhammadyusuf.kurbonov.defaultresources.EmptyPage
import uz.muhammadyusuf.kurbonov.defaultresources.ErrorPage
import uz.muhammadyusuf.kurbonov.defaultresources.LoadingPage
import uz.muhammadyusuf.kurbonov.defaultresources.R
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.DetailsLayout
import uz.muhammadyusuf.kurbonov.utils.formatAsDate

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
fun DetailsScreen(
    showState: MutableState<Boolean>,
    item: AccountingGroup
) {

    DetailsLayout(showState = showState) {
        Text(
            text = stringResource(
                id = R.string.details
            ),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth().padding(defaultPadding())
        )

        Text(
            text = "Date ${item.groupItem.date.formatAsDate()}",
            modifier = Modifier.fillMaxWidth().padding(defaultMargin())
        )

        LazyColumnFor(items = item.items) {

            Row(modifier = Modifier.fillMaxWidth().padding(defaultPadding())) {
                Text(text = it.itemName)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = it.totalSum.toString())
            }

            Divider()
        }

        Button(onClick = { showState.value = false }) {
            Text(text = stringResource(id = R.string.close))
        }
    }
}


@ExperimentalCoroutinesApi
@Composable
fun AddEditScreen(
    showState: MutableState<Boolean>,
    item: AccountingGroup? = null,
    viewModel: AddEditViewModel
) {
    if (showState.value) {

        val itemName = remember { mutableStateOf("") }
        val itemError = remember { mutableStateOf(false) }
        val itemSum = remember { mutableStateOf(0) }
        val date = remember { mutableStateOf(System.currentTimeMillis()) }
        val list = viewModel.getItems().collectAsState()

        Dialog(onDismissRequest = {
            showState.value = false
        }) {

            Card(
                backgroundColor = MaterialTheme.colors.background,
                shape = RoundedCornerShape(8.dp)
            ) {
                Box {
                    Image(asset = vectorResource(id = R.drawable.ic_action_name))
                    Column(modifier = Modifier.fillMaxWidth().padding(defaultPadding())) {
                        Text(
                            text = stringResource(
                                id = if (item != null) R.string.edit_label
                                else R.string.add_label
                            ),
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(defaultPadding())
                        )

                        Card(
                            elevation = 0.dp,
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colors.onBackground.copy(alpha = 0.42f)
                            ),
                            modifier = Modifier.padding(defaultPadding())
                        ) {
                            Text(
                                text = "Date: ${date.value.formatAsDate()}",
                                modifier = Modifier.padding(defaultPadding())
                            )
                        }

                        LazyColumn {
                            items(items = list.value) {
                                Row(modifier = Modifier.fillMaxWidth().padding(defaultPadding())) {
                                    Text(text = it.itemName)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(text = it.totalSum.toString())
                                }

                                Divider()
                            }

                            item {

                                Row(modifier = Modifier.fillMaxWidth().padding(defaultPadding())) {
                                    OutlinedTextField(
                                        value = itemName.value,
                                        onValueChange = { itemName.component2().invoke(it) },
                                        modifier = Modifier.weight(2.0f),
                                        isErrorValue = itemError.value
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    OutlinedTextField(
                                        value = itemSum.value.toString(),
                                        onValueChange = {
                                            itemSum.value = try {
                                                it.toInt()
                                            } catch (e: Exception) {
                                                0
                                            }
                                        },
                                        modifier = Modifier.weight(1.0f),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                    Button(
                                        onClick = {
                                            if (itemName.value.isEmpty()) {
                                                itemError.value = true
                                                return@Button
                                            }
                                            viewModel.addItem(
                                                AccountingItem(
                                                    itemName = itemName.value,
                                                    totalSum = itemSum.value
                                                )
                                            )
                                            itemName.value = ""
                                            itemSum.value = 0
                                        }, modifier = Modifier.weight(1f)
                                            .align(Alignment.CenterVertically)
                                            .fillMaxSize()
                                            .padding(defaultPadding())
                                    ) {
                                        Image(
                                            asset = Icons.Default.Add, colorFilter = ColorFilter(
                                                Color.Green, BlendMode.SrcAtop
                                            ),
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }


                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Preview(showBackground = true)
@Composable
fun previewAddEdit() {
    AddEditScreen(showState = mutableStateOf(true), viewModel = AddEditViewModel())
}

