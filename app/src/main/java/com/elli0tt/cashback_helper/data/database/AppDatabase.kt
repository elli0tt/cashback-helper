package com.elli0tt.cashback_helper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elli0tt.cashback_helper.data.database.dao.BankCardsCashbackCategoriesDao
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryXRefEntity
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity

@Database(
    entities = [
        BankCardEntity::class,
        CashbackCategoryEntity::class,
        BankCardCashbackCategoryXRefEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBankCardsCashbackCategoriesDao(): BankCardsCashbackCategoriesDao

    companion object {
        const val NAME = "app-database"
    }
}