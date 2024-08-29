package com.elli0tt.cashback_helper.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before

open class BaseDatabaseTest {
    protected lateinit var database: AppDatabase

    @Before
    open fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    fun cleanup() {
        database.close()
    }
}