package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem
import uz.muhammadyusuf.kurbonov.studentsaccounting.R
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultMargin
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultPadding
import uz.muhammadyusuf.kurbonov.utils.reformatDate
import kotlin.math.absoluteValue

@Composable
fun MainListItem(item: AccountingItem) {
    Card(
        elevation = 10.dp, modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Padding(paddingValues = PaddingValues(defaultMargin())) {

            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(
                    modifier = Modifier
                        .padding(defaultPadding()),
                    text = item.itemDescription,
                    style = MaterialTheme.typography.h6,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(defaultPadding()),
                    text = item.author
                )
            }

            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
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

                Text(
                    modifier = Modifier
                        .padding(defaultPadding()),
                    text = item.date.reformatDate("yyyy-MM-DD", "dd MMM YYYY"),
                    fontStyle = FontStyle.Italic,
                )

                Text(
                    modifier = Modifier
                        .padding(defaultPadding()),
                    text = item.totalSum.toString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewListItem() {

    Padding(paddingValues = PaddingValues(defaultMargin())) {
        Column {
            MainListItem(
                AccountingItem(
                    author = "Muhammadyusuf",
                    itemDescription = "Test item #1",
                    totalSum = 530
                )
            )

            MainListItem(
                AccountingItem(
                    author = "Fayoziddin",
                    itemDescription = "Test item #2",
                    totalSum = -230
                )
            )
        }
    }
}