package uz.muhammadyusuf.kurbonov.repository.models

import androidx.room.Embedded
import androidx.room.Relation


class AccountingGroup(
    @Embedded
    var groupItem: AccountingGroupItem = AccountingGroupItem(),
    @Relation(
        entity = AccountingItem::class,
        parentColumn = "id",
        entityColumn = "groupId"
    )
    var items: List<AccountingItem>
)