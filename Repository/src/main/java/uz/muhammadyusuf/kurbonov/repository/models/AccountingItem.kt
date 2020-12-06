package uz.muhammadyusuf.kurbonov.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.muhammadyusuf.kurbonov.utils.formatAsDate

@Entity
data class AccountingItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var itemDescription: String = "",
    var totalSum: Double = 0.0,
    var author: String = "Muhammadyusuf",
    var date: String = System.currentTimeMillis().formatAsDate("yyyy-MM-DD")
)