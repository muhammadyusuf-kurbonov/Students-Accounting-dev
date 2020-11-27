package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.ui.tooling.preview.Preview
import uz.muhammadyusuf.kurbonov.defaultresources.R
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroupItem
import uz.muhammadyusuf.kurbonov.utils.formatAsDate

@Composable
fun MainListItem(modifier: Modifier = Modifier, accountingGroup: AccountingGroup) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
            Text(
                text = accountingGroup.groupItem.description,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.weight(1.0f))

            Text(text = accountingGroup.groupItem.date.formatAsDate())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewListItem() {
    MainListItem(
        accountingGroup = AccountingGroup(
            groupItem = AccountingGroupItem(description = "Hallo world"),
            emptyList()
        )
    )
}