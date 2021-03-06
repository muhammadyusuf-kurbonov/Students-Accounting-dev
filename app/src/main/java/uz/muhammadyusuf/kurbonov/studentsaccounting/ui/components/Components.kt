package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import uz.muhammadyusuf.kurbonov.studentsaccounting.R
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultMargin
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultPadding

@Composable
fun <T> Spinner(
    modifier: Modifier = Modifier,
    items: List<T> = emptyList(),
    itemSelected: (T?) -> Unit = {}
) {
    AndroidView(
        viewBlock = { context ->
            val spinner = android.widget.Spinner(context)

            spinner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1).apply {
                    addAll(items.map { it.toString() })
                }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    itemSelected(items[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    itemSelected(null)
                }

            }

            spinner
        },
        modifier
    )
}

@Composable
fun BalanceReport(modifier: Modifier = Modifier, sumState: State<Double?>) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.balance_label),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(defaultPadding())
        )

        Text(
            text = sumState.value.toString(),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(
                defaultMargin()
            )
        )
    }
}