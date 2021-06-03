package pl.sokols.shoppinglist.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.sokols.shoppinglist.data.database.AppDatabase
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.data.entities.ShopListDetails
import pl.sokols.shoppinglist.getOrAwaitValue
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShopListDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: ShopListDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.shopListDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testGetAllShopListsByActive() = runBlockingTest {
        val shopList1 = ShopList(
            name = "testList",
            date = Calendar.getInstance().time,
            isActive = true,
            items = listOf()
        )
        val shopList2 = ShopList(
            name = "testList",
            date = Calendar.getInstance().time,
            isActive = false,
            items = listOf()
        )
        dao.insertShopList(shopList1)
        dao.insertShopList(shopList2)
        val allShopList = dao.getAllShopListsByActive(true).asLiveData().getOrAwaitValue()
        assertThat(allShopList).contains(ShopListDetails(shopList1))
        assertThat(allShopList).doesNotContain(ShopListDetails(shopList2))
    }

    @Test
    fun testUpdateShopListActive() = runBlockingTest {
        val shopList = ShopList(
            name = "testList",
            date = Calendar.getInstance().time,
            isActive = true,
            items = listOf()
        )
        dao.insertShopList(shopList)
        val addedList = dao.getAllShopListsByActive(true).asLiveData().getOrAwaitValue()[0]
        addedList.shopList.isActive = !addedList.shopList.isActive
        dao.updateShopList(addedList.shopList)
        val allShopItems = dao.getAllShopListsByActive(false).asLiveData().getOrAwaitValue()
        assertThat(allShopItems).contains(addedList)
    }

    @Test
    fun testInsertShopList() = runBlockingTest {
        val shopList = ShopList(
            name = "testList",
            date = Calendar.getInstance().time,
            isActive = true,
            items = listOf()
        )
        dao.insertShopList(shopList)
        val allShopLists = dao.getAllShopListsByActive(true).asLiveData().getOrAwaitValue()
        assertThat(allShopLists).contains(ShopListDetails(shopList))
    }

    @Test
    fun testDeleteShopList() = runBlockingTest {
        val shopList = ShopList(
            name = "testList",
            date = Calendar.getInstance().time,
            isActive = true,
            items = listOf()
        )
        dao.insertShopList(shopList)
        val addedList = dao.getAllShopListsByActive(true).asLiveData().getOrAwaitValue()[0]
        dao.deleteShopList(addedList.shopList)
        val allShopLists = dao.getAllShopListsByActive(true).asLiveData().getOrAwaitValue()
        assertThat(allShopLists).doesNotContain(addedList)
    }
}