package com.elli0tt.cashback_helper.data.database

import com.elli0tt.cashback_helper.data.database.base.BaseDatabaseTest
import com.elli0tt.cashback_helper.data.database.dao.BankCardsCashbackCategoriesDao
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BankCardsCashbackCategoriesDaoTest : BaseDatabaseTest() {

    private lateinit var bankCardsCashbackCategoriesDao: BankCardsCashbackCategoriesDao

    @Before
    override fun setup() {
        super.setup()
        bankCardsCashbackCategoriesDao = database.bankCardsCashbackCategoriesDao
    }

    @Test
    fun checkGetAllCategoriesWithZeroCategories() = runTest {
        val cashbackCategoriesDeferred = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllCategories().first()
        }

        val cashbackCategoriesFromDatabase = cashbackCategoriesDeferred.await()
        Assert.assertEquals(0, cashbackCategoriesFromDatabase.size)
    }

    @Test
    fun checkGetAllCategoriesWithOneCategory() = runTest {
        val cashbackCategoriesDeferred = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllCategories().first()
        }

        val cashbackCategoryEntity = CashbackCategoryEntity("cashbackCategory")
        bankCardsCashbackCategoriesDao.insertCategory(cashbackCategoryEntity)

        val cashbackCategoriesFromDatabase = cashbackCategoriesDeferred.await()
        Assert.assertEquals(1, cashbackCategoriesFromDatabase.size)
        Assert.assertEquals(cashbackCategoryEntity, cashbackCategoriesFromDatabase.first())
    }

    @Test
    fun checkGetAllCategoriesWithMultipleCategories() = runTest {
        val cashbackCategoriesDeferred = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllCategories().first()
        }

        val categoriesCount = 5
        val expectedCashbackCategories = List(categoriesCount) { index ->
            CashbackCategoryEntity("cashbackCategory $index")
        }
        bankCardsCashbackCategoriesDao.insertCategories(expectedCashbackCategories)

        val cashbackCategoriesFromDatabase = cashbackCategoriesDeferred.await()
        Assert.assertEquals(categoriesCount, cashbackCategoriesFromDatabase.size)
        Assert.assertEquals(expectedCashbackCategories, cashbackCategoriesFromDatabase)
    }

    @Test
    fun checkGetAllBankCardsWithZeroBankCards() = runTest {
        val bankCards = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllBankCards().first()
        }.await()

        Assert.assertEquals(0, bankCards.size)
    }

    @Test
    fun checkGetAllBankCardsWithOneCategory() = runTest {
        val bankCardsDeferred = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllBankCards().first()
        }

        val bankCardEntity = BankCardEntity(name = "Bank Card", order = 0)
        bankCardsCashbackCategoriesDao.insertBankCard(bankCardEntity)

        val bankCardsFromDatabase = bankCardsDeferred.await()
        Assert.assertEquals(1, bankCardsFromDatabase.size)
        Assert.assertEquals(bankCardEntity, bankCardsFromDatabase.first())
    }

    @Test
    fun checkGetAllBankCardsWithMultipleCategories() = runTest {
        val bankCardsDeferred = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllBankCards().first()
        }

        val bankCardsCount = 5
        val expectedBankCards = List(bankCardsCount) { index ->
            BankCardEntity(name = "Bank Card $index", order = index)
        }
        bankCardsCashbackCategoriesDao.insertBankCards(expectedBankCards)

        val bankCardsFromDatabase = bankCardsDeferred.await()
        Assert.assertEquals(bankCardsCount, bankCardsFromDatabase.size)
        Assert.assertEquals(expectedBankCards, bankCardsFromDatabase)
    }

    @Test
    fun checkCategoriesCountWithZeroCategoriesInDatabase() = runTest {
        Assert.assertEquals(0, bankCardsCashbackCategoriesDao.getCategoriesCount())
    }

    @Test
    fun checkCategoriesCountWithOneCategoryInDatabase() = runTest {
        bankCardsCashbackCategoriesDao.insertCategory(
            CashbackCategoryEntity(name = "cashbackCategory")
        )
        Assert.assertEquals(1, bankCardsCashbackCategoriesDao.getCategoriesCount())
    }

    @Test
    fun checkCategoriesCountWithMultipleCategoriesInDatabase() = runTest {
        val categoriesCount = 5
        bankCardsCashbackCategoriesDao.insertCategories(
            List(categoriesCount) { index ->
                CashbackCategoryEntity(name = "cashbackCategory $index")
            }
        )
        Assert.assertEquals(categoriesCount, bankCardsCashbackCategoriesDao.getCategoriesCount())
    }

    @Test
    fun checkBankCardsCountWithZeroCategoriesInDatabase() = runTest {
        Assert.assertEquals(0, bankCardsCashbackCategoriesDao.getBankCardsCount())
    }

    @Test
    fun checkBankCardsCountWithOneCategoryInDatabase() = runTest {
        bankCardsCashbackCategoriesDao.insertBankCard(BankCardEntity(name = "Bank Card", order = 0))
        Assert.assertEquals(1, bankCardsCashbackCategoriesDao.getBankCardsCount())
    }

    @Test
    fun checkBankCardsCountWithMultipleCategoriesInDatabase() = runTest {
        val bankCardsCount = 5
        bankCardsCashbackCategoriesDao.insertBankCards(
            List(bankCardsCount) { index ->
                BankCardEntity(name = "Bank Card $index", order = index)
            }
        )
        Assert.assertEquals(bankCardsCount, bankCardsCashbackCategoriesDao.getBankCardsCount())
    }
}