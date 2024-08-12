package com.elli0tt.cashback_helper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elli0tt.cashback_helper.data.database.dao.BankCardsDao
import com.elli0tt.cashback_helper.data.database.dao.CashbackCategoriesDao
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity

@Database(entities = [BankCardEntity::class, CashbackCategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val bankCardsDao: BankCardsDao
    abstract val cashbackCategoriesDao: CashbackCategoriesDao

    companion object {
        const val NAME = "app-database"
    }
}