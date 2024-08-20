package com.elli0tt.cashback_helper.di

import android.content.Context
import androidx.room.Room
import com.elli0tt.cashback_helper.data.database.AppDatabase
import com.elli0tt.cashback_helper.data.database.dao.BankCardsDao
import com.elli0tt.cashback_helper.data.database.dao.CashbackCategoriesDao
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DatabaseModule {

    @Single
    fun appDatabase(appContext: Context): AppDatabase = Room
        .databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.NAME)
        .build()

    @Single
    fun bankCardsDao(appDatabase: AppDatabase): BankCardsDao = appDatabase.bankCardsDao

    @Single
    fun cashbackCategoryDao(appDatabase: AppDatabase): CashbackCategoriesDao =
        appDatabase.cashbackCategoriesDao
}