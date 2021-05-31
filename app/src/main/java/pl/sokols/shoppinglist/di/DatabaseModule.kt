package pl.sokols.shoppinglist.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.sokols.shoppinglist.data.dao.ShopItemDao
import pl.sokols.shoppinglist.data.dao.ShopListDao
import pl.sokols.shoppinglist.data.database.AppDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideShopItemDao(appDatabase: AppDatabase): ShopItemDao = appDatabase.shopItemDao()

    @Singleton
    @Provides
    fun provideShopListDao(appDatabase: AppDatabase): ShopListDao = appDatabase.shopListDao()
}