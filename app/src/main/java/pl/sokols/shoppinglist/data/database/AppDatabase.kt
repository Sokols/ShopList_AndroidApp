package pl.sokols.shoppinglist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.sokols.shoppinglist.data.dao.ShopItemDao
import pl.sokols.shoppinglist.data.dao.ShopListDao
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.data.entities.ShopList

@Database(entities = [ShopItem::class, ShopList::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopItemDao(): ShopItemDao

    abstract fun shopListDao(): ShopListDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shopping_list_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}