package com.elli0tt.cashback_helper.data.database

import com.elli0tt.cashback_helper.data.database.base.BaseDatabaseTest
import com.elli0tt.cashback_helper.data.database.dao.BankCardsDao
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BankCardsDaoTest : BaseDatabaseTest() {

    private lateinit var bankCardsDao: BankCardsDao

    @Before
    override fun setup() {
        super.setup()
        bankCardsDao = database.bankCardsDao
    }

    @Test
    fun checkGetAllBankCardsWithZeroBankCards() = runTest {
        val bankCards = backgroundScope.async {
            bankCardsDao.getAllBankCards().first()
        }.await()

        Assert.assertEquals(0, bankCards.size)
    }

    @Test
    fun checkGetAllBankCardsWithOneCategory() = runTest {
        val bankCardsDeferred = backgroundScope.async {
            bankCardsDao.getAllBankCards().first()
        }

        val bankCardEntity = BankCardEntity(name = "Bank Card", order = 0)
        bankCardsDao.insertBankCard(bankCardEntity)

        val bankCardsFromDatabase = bankCardsDeferred.await()
        Assert.assertEquals(1, bankCardsFromDatabase.size)
        Assert.assertEquals(bankCardEntity, bankCardsFromDatabase.first())
    }

    @Test
    fun checkGetAllBankCardsWithMultipleCategories() = runTest {
        val bankCardsDeferred = backgroundScope.async {
            bankCardsDao.getAllBankCards().first()
        }

        val bankCardsCount = 5
        val expectedBankCards = List(bankCardsCount) { index ->
            BankCardEntity(name = "Bank Card $index", order = index)
        }
        bankCardsDao.insertBankCards(expectedBankCards)

        val bankCardsFromDatabase = bankCardsDeferred.await()
        Assert.assertEquals(bankCardsCount, bankCardsFromDatabase.size)
        Assert.assertEquals(expectedBankCards, bankCardsFromDatabase)
    }

    @Test
    fun checkBankCardsCountWithZeroCategoriesInDatabase() = runTest {
        Assert.assertEquals(0, bankCardsDao.getBankCardsCount())
    }

    @Test
    fun checkCategoriesCountWithOneCategoryInDatabase() = runTest {
        bankCardsDao.insertBankCard(BankCardEntity(name = "Bank Card", order = 0))
        Assert.assertEquals(1, bankCardsDao.getBankCardsCount())
    }

    @Test
    fun checkCategoriesCountWithMultipleCategoriesInDatabase() = runTest {
        val bankCardsCount = 5
        bankCardsDao.insertBankCards(
            List(bankCardsCount) { index ->
                BankCardEntity(name = "Bank Card $index", order = index)
            }
        )
        Assert.assertEquals(bankCardsCount, bankCardsDao.getBankCardsCount())
    }
}