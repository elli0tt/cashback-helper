package com.elli0tt.cashback_helper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elli0tt.cashback_helper.data.database.dao.CashbackCategoryDao
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity

@Database(entities = [CashbackCategoryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val cashbackCategoryDao: CashbackCategoryDao

    companion object {
        const val NAME = "app-database"
    }
}