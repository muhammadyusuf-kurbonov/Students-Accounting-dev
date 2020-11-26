package uz.muhammadyusuf.kurbonov.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountingGroupItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var description: String = "",
    var totalSum: Int = 0,
    var date: Long = System.currentTimeMillis()
)