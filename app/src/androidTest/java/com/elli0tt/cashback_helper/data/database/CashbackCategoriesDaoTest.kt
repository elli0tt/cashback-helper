package com.elli0tt.cashback_helper.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.elli0tt.cashback_helper.data.database.dao.CashbackCategoriesDao
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CashbackCategoriesDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var cashbackCategoriesDao: CashbackCategoriesDao

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        cashbackCategoriesDao = database.cashbackCategoriesDao
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun checkCategoriesCountWithZeroCategoriesInDatabase() = runTest {
        Assert.assertEquals(0, cashbackCategoriesDao.getCategoriesCount())
    }

    @Test
    fun checkCategoriesCountWithOneCategoryInDatabase() = runTest {
        cashbackCategoriesDao.insertCategory(CashbackCategoryEntity(name = "cashbackCategory"))
        Assert.assertEquals(1, cashbackCategoriesDao.getCategoriesCount())
    }

    @Test
    fun checkCategoriesCountWithMultipleCategoriesInDatabase() = runTest {
        val categoriesCount = 5
        cashbackCategoriesDao.insertCategories(
            List(categoriesCount) { index ->
                CashbackCategoryEntity(name = "cashbackCategory $index")
            }
        )
        Assert.assertEquals(categoriesCount, cashbackCategoriesDao.getCategoriesCount())
    }

    @Test
    fun checkGetAllCategoriesWithZeroCategories() = runTest {
        val cashbackCategoriesDeferred = backgroundScope.async {
            cashbackCategoriesDao.getAllCategories().first()
        }

        val cashbackCategoriesFromDatabase = cashbackCategoriesDeferred.await()
        Assert.assertEquals(0, cashbackCategoriesFromDatabase.size)
    }

    @Test
    fun checkGetAllCategoriesWithOneCategory() = runTest {
        val cashbackCategoriesDeferred = backgroundScope.async {
            cashbackCategoriesDao.getAllCategories().first()
        }

        val cashbackCategoryEntity = CashbackCategoryEntity("cashbackCategory")
        cashbackCategoriesDao.insertCategory(cashbackCategoryEntity)

        val cashbackCategoriesFromDatabase = cashbackCategoriesDeferred.await()
        Assert.assertEquals(1, cashbackCategoriesFromDatabase.size)
        Assert.assertEquals(cashbackCategoryEntity, cashbackCategoriesFromDatabase.first())
    }

    @Test
    fun checkGetAllCategoriesWithMultipleCategories() = runTest {
        val cashbackCategoriesDeferred = backgroundScope.async {
            cashbackCategoriesDao.getAllCategories().first()
        }

        val categoriesCount = 5
        val expectedCashbackCategories = List(categoriesCount) { index ->
            CashbackCategoryEntity("cashbackCategory $index")
        }
        cashbackCategoriesDao.insertCategories(expectedCashbackCategories)

        val cashbackCategoriesFromDatabase = cashbackCategoriesDeferred.await()
        Assert.assertEquals(categoriesCount, cashbackCategoriesFromDatabase.size)
        Assert.assertEquals(expectedCashbackCategories, cashbackCategoriesFromDatabase)
    }
}