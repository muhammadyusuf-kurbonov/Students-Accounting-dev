package uz.muhammadyusuf.kurbonov.repository.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = AccountingGroupItem::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AccountingItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var itemName: String = "",
    var totalSum: Int = 0,
    var groupId: Int = -1
)