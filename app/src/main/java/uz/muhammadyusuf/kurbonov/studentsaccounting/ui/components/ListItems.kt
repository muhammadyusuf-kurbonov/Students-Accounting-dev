package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultMargin
import uz.muhammadyusuf.kurbonov.utils.prettifyDate
import kotlin.math.absoluteValue

@Composable
fun MainListItem(
    item: AccountingItem,
    onClick: (AccountingItem) -> Unit = {}
) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(item) })
            .background(
                if (item.totalSum < 0) Color.Red.copy(alpha = 0.42f)
                else Color.Green.copy(alpha = 0.42f),
                shape = RoundedCornerShape(8.dp)
            ).alpha(0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Padding(paddingValues = PaddingValues(defaultMargin())) {

            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(defaultPadding()),
                        text = item.date.prettifyDate(),
                        fontStyle = FontStyle.Italic,
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding())
                ) {

                    Text(
                        modifier = Modifier
                            .padding(defaultPadding())
                            .weight(2.0f),
                        text = item.itemDescription,
                        style = MaterialTheme.typography.h6,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        softWrap = true,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier
                            .padding(defaultPadding())
                            .weight(1.0f),
                        text = item.totalSum.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                }



                Row(modifier = Modifier.weight(1f)) {
                    Text(
                        modifier = Modifier.padding(defaultPadding()),
                        text = item.totalSum.absoluteValue.toString(),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        imageVector = vectorResource(
                            id = if (item.totalSum > 0) R.drawable.ic_baseline_arrow_drop_up_24
                            else R.drawable.ic_baseline_arrow_drop_down_24
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListItem() {

    Padding(paddingValues = PaddingValues(defaultMargin())) {
        Column {
            MainListItem(
                AccountingItem(
                    itemDescription = "Test item #1",
                    totalSum = 530.0
                )
            )

            MainListItem(
                AccountingItem(
                    itemDescription = "Test item #2",
                    totalSum = -230.0
                )
            )
        }
    }
}