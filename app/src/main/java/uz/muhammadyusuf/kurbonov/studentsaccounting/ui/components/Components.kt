package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

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