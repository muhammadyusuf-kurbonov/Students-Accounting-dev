package uz.muhammadyusuf.kurbonov.repository

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroup
import uz.muhammadyusuf.kurbonov.repository.models.AccountingGroupItem
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem
import java.util.*
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var database: AppDatabase

    @Before
    fun initDatabase() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = AppDatabase.getInstance(appContext, true)
    }

    private fun getSamples(): List<AccountingGroup> {
        val result = mutableListOf<AccountingGroup>()
        repeat(Random.nextInt(1, 25)) {
            result.add(getSampleGroup())
        }
        return result
    }

    private fun getSampleGroup(): AccountingGroup {
        val id = Random.nextInt()
        val listItems = mutableListOf<AccountingItem>()
        repeat(Random.nextInt(0, 10)) {
            listItems.add(
                AccountingItem(
                    itemName = UUID.randomUUID().toString(),
                    totalSum = Random.nextInt(1, 50) * 1000,
                    groupId = id
                )
            )
        }
        return AccountingGroup(
            AccountingGroupItem(
                id = id,
                description = UUID.randomUUID().toString(),
                totalSum = 5_000
            ),
            listItems
        )
    }

    @Test
    fun insertQueryAllTest() {
        // Context of the app under test.
        val testGroup = getSamples()[0]
        print("Hallo")
        runBlocking {
            database.getAccountingObject().insertGroup(testGroup)
            assertEquals(1, database.getAccountingObject().getAllGroups().size)
            database.getAccountingObject().getAllGroups().forEach {
                assertEquals(testGroup.groupItem.description, it.groupItem.description)
                assertEquals(testGroup.items.size, it.items.size)
                Log.d("Items", it.toString())
            }
        }
    }

    @Test
    fun testUpdate() {
        val data = getSamples()
        runBlocking {
            data.forEach {
                database.getAccountingObject().insertGroup(it)
            }
            val item = data.shuffled()[0]
            item.groupItem.description = "Test for update"
            database.getAccountingObject().updateGroup(item)
            assertEquals(
                item.groupItem.description,
                database.getAccountingObject().getGroup(item.groupItem.id).groupItem.description
            )
        }
    }

}