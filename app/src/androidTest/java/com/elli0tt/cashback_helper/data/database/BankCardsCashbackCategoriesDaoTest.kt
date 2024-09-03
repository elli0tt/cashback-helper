package com.elli0tt.cashback_helper.data.database

import com.elli0tt.cashback_helper.data.database.base.BaseDatabaseTest
import com.elli0tt.cashback_helper.data.database.dao.BankCardsCashbackCategoriesDao
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryXRefEntity
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

        val expectedCashbackCategories = generateCashbackCategoriesEntities(5)
        bankCardsCashbackCategoriesDao.insertCategories(expectedCashbackCategories)

        val cashbackCategoriesFromDatabase = cashbackCategoriesDeferred.await()
        Assert.assertEquals(expectedCashbackCategories.size, cashbackCategoriesFromDatabase.size)
        Assert.assertEquals(expectedCashbackCategories, cashbackCategoriesFromDatabase)
    }

    @Test
    fun checkGetAllBankCardsWithZeroCards() = runTest {
        val bankCards = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllCards().first()
        }.await()

        Assert.assertEquals(0, bankCards.size)
    }

    @Test
    fun checkGetAllCardsWithOneCategory() = runTest {
        val bankCardsDeferred = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllCards().first()
        }

        val bankCardEntity = BankCardEntity(name = "Bank Card", order = 0)
        bankCardsCashbackCategoriesDao.insertCard(bankCardEntity)

        val bankCardsFromDatabase = bankCardsDeferred.await()
        Assert.assertEquals(1, bankCardsFromDatabase.size)
        Assert.assertEquals(bankCardEntity, bankCardsFromDatabase.first())
    }

    @Test
    fun checkGetAllCardsWithMultipleCategories() = runTest {
        val bankCardsDeferred = backgroundScope.async {
            bankCardsCashbackCategoriesDao.getAllCards().first()
        }

        val expectedBankCards = generateBankCardsEntities(5)
        bankCardsCashbackCategoriesDao.insertCards(expectedBankCards)

        val bankCardsFromDatabase = bankCardsDeferred.await()
        Assert.assertEquals(expectedBankCards.size, bankCardsFromDatabase.size)
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
        Assert.assertEquals(0, bankCardsCashbackCategoriesDao.getCardsCount())
    }

    @Test
    fun checkBankCardsCountWithOneCategoryInDatabase() = runTest {
        bankCardsCashbackCategoriesDao.insertCard(BankCardEntity(name = "Bank Card", order = 0))
        Assert.assertEquals(1, bankCardsCashbackCategoriesDao.getCardsCount())
    }

    @Test
    fun checkBankCardsCountWithMultipleCategoriesInDatabase() = runTest {
        val bankCardsCount = 5
        bankCardsCashbackCategoriesDao.insertCards(
            List(bankCardsCount) { index ->
                BankCardEntity(name = "Bank Card $index", order = index)
            }
        )
        Assert.assertEquals(bankCardsCount, bankCardsCashbackCategoriesDao.getCardsCount())
    }

//    @Test
//    fun checkGetCardsWithCategoriesWithOneItem() = runTest {
////        val cardsWithCategoriesDeferred = backgroundScope.async {
////            bankCardsCashbackCategoriesDao.getCardsWithCategories().first()
////        }
//
//        val cashbackCategoryEntity = CashbackCategoryEntity(name = "Cashback Category")
//        val bankCardEntity = BankCardEntity(name = "BankCard", order = 0)
//        bankCardsCashbackCategoriesDao.insertCategory(cashbackCategoryEntity)
//        bankCardsCashbackCategoriesDao.insertCard(bankCardEntity)
//        bankCardsCashbackCategoriesDao.insertCardCategoryXRef(
//            BankCardCashbackCategoryXRefEntity(
//                bankCardName = bankCardEntity.name,
//                cashbackCategoryName = cashbackCategoryEntity.name,
//                isSelected = false,
//                percent = 100f
//            )
//        )
//
////        val cardsWithCategories = cardsWithCategoriesDeferred.await()
//        val cardsWithCategories = bankCardsCashbackCategoriesDao.getCardsWithCategories()
//        Assert.assertEquals(1, cardsWithCategories.size)
//        Assert.assertEquals(
//            cashbackCategoryEntity.name,
//            cardsWithCategories.first().cashbackCategoryName
//        )
//        Assert.assertEquals(bankCardEntity.name, cardsWithCategories.first().bankCardName)
//        Assert.assertEquals(100f, cardsWithCategories.first().percent)
//    }
//
//    @Test
//    fun checkGetCardsWithCategoriesWithMultipleItemsFullCross() = runTest {
////        val cardsWithCategoriesDeferred = backgroundScope.async {
////            bankCardsCashbackCategoriesDao.getCardsWithCategories().first()
////        }
//
//        val size = 5
//        val cashbackCategoryEntities = generateCashbackCategoriesEntities(size)
//        val bankCardEntities = generateBankCardsEntities(size)
//        val bankCardsCashbackCategoriesXRefs = List(size) { index ->
//            BankCardCashbackCategoryXRefEntity(
//                bankCardName = bankCardEntities[index].name,
//                cashbackCategoryName = cashbackCategoryEntities[index].name,
//                isSelected = false,
//                percent = 10f * index
//            )
//        }
//        bankCardsCashbackCategoriesDao.insertCategories(cashbackCategoryEntities)
//        bankCardsCashbackCategoriesDao.insertCards(bankCardEntities)
//        bankCardsCashbackCategoriesDao.insertCardsCategoriesXRefs(bankCardsCashbackCategoriesXRefs)
//
////        val cardsWithCategories = cardsWithCategoriesDeferred.await()
//        val cardsWithCategories = bankCardsCashbackCategoriesDao.getCardsWithCategories()
//        Assert.assertEquals(size, cardsWithCategories.size)
//        cardsWithCategories.forEachIndexed { index, cardWithCategories ->
//            Assert.assertEquals(
//                cashbackCategoryEntities[index].name,
//                cardWithCategories.cashbackCategoryName
//            )
//            Assert.assertEquals(bankCardEntities[index].name, cardWithCategories.bankCardName)
//            Assert.assertEquals(
//                bankCardsCashbackCategoriesXRefs[index].percent,
//                cardWithCategories.percent
//            )
//        }
//    }
//
//    @Test
//    fun checkGetCardsWithCategoriesWithMultipleItems2Crosses() = runTest {
////        val cardsWithCategoriesDeferred = backgroundScope.async {
////            bankCardsCashbackCategoriesDao.getCardsWithCategories().first()
////        }
//
//        val size = 5
//        val cashbackCategoryEntities = generateCashbackCategoriesEntities(size)
//        val bankCardEntities = generateBankCardsEntities(size)
//        val bankCardsCashbackCategoriesXRefs = List(2) { index ->
//            BankCardCashbackCategoryXRefEntity(
//                bankCardName = bankCardEntities[index].name,
//                cashbackCategoryName = cashbackCategoryEntities[index].name,
//                isSelected = false,
//                percent = 10f * index
//            )
//        }
//        bankCardsCashbackCategoriesDao.insertCategories(cashbackCategoryEntities)
//        bankCardsCashbackCategoriesDao.insertCards(bankCardEntities)
//        bankCardsCashbackCategoriesDao.insertCardsCategoriesXRefs(bankCardsCashbackCategoriesXRefs)
//
////        val cardsWithCategories = cardsWithCategoriesDeferred.await()
//        val cardsWithCategories = bankCardsCashbackCategoriesDao.getCardsWithCategories()
//        val expectedCardsWithCategories = listOf(
//            BankCardsCashbackCategoriesDao.BankCardCashbackCategoryPercent(
//                bankCardName = bankCardEntities[0].name,
//                cashbackCategoryName = cashbackCategoryEntities[0].name,
//                percent = bankCardsCashbackCategoriesXRefs[0].percent
//            ),
//            BankCardsCashbackCategoriesDao.BankCardCashbackCategoryPercent(
//                bankCardName = bankCardEntities[1].name,
//                cashbackCategoryName = cashbackCategoryEntities[1].name,
//                percent = bankCardsCashbackCategoriesXRefs[1].percent
//            ),
//            BankCardsCashbackCategoriesDao.BankCardCashbackCategoryPercent(
//                bankCardName = bankCardEntities[2].name,
//                cashbackCategoryName = null,
//                percent = null
//            ),
//            BankCardsCashbackCategoriesDao.BankCardCashbackCategoryPercent(
//                bankCardName = bankCardEntities[3].name,
//                cashbackCategoryName = null,
//                percent = null
//            ),
//            BankCardsCashbackCategoriesDao.BankCardCashbackCategoryPercent(
//                bankCardName = bankCardEntities[4].name,
//                cashbackCategoryName = null,
//                percent = null
//            ),
//        )
//        Assert.assertEquals(size, cardsWithCategories.size)
//        cardsWithCategories.forEachIndexed { index, cardWithCategories ->
//            Assert.assertEquals(
//                expectedCardsWithCategories[index].cashbackCategoryName,
//                cardWithCategories.cashbackCategoryName
//            )
//            Assert.assertEquals(
//                expectedCardsWithCategories[index].bankCardName,
//                cardWithCategories.bankCardName
//            )
//            Assert.assertEquals(
//                expectedCardsWithCategories[index].percent,
//                cardWithCategories.percent
//            )
//        }
//    }
//
//    @Test
//    fun checkGetCardsWithCategoriesWithMultipleItems0Crosses() = runTest {
////        val cardsWithCategoriesDeferred = backgroundScope.async {
////            bankCardsCashbackCategoriesDao.getCardsWithCategories().first()
////        }
//
//        val size = 5
//        val cashbackCategoryEntities = generateCashbackCategoriesEntities(size)
//        val bankCardEntities = generateBankCardsEntities(size)
//
//        bankCardsCashbackCategoriesDao.insertCategories(cashbackCategoryEntities)
//        bankCardsCashbackCategoriesDao.insertCards(bankCardEntities)
//
////        val cardsWithCategories = cardsWithCategoriesDeferred.await()
//        val cardsWithCategories = bankCardsCashbackCategoriesDao.getCardsWithCategories()
//        Assert.assertEquals(size, cardsWithCategories.size)
//        cardsWithCategories.forEachIndexed { index, cardWithCategories ->
//            Assert.assertEquals(bankCardEntities[index].name, cardWithCategories.bankCardName)
//            Assert.assertEquals(null, cardWithCategories.cashbackCategoryName)
//            Assert.assertEquals(null, cardWithCategories.percent)
//        }
//    }
//
//    @Test
//    fun checkGetCardsWithCategoriesWithMultipleItems0Cards() = runTest {
////        val cardsWithCategoriesDeferred = backgroundScope.async {
////            bankCardsCashbackCategoriesDao.getCardsWithCategories().first()
////        }
//
//        val size = 5
//        val cashbackCategoryEntities = generateCashbackCategoriesEntities(size)
//
//        bankCardsCashbackCategoriesDao.insertCategories(cashbackCategoryEntities)
//
////        val cardsWithCategories = cardsWithCategoriesDeferred.await()
//        val cardsWithCategories = bankCardsCashbackCategoriesDao.getCardsWithCategories()
//        Assert.assertEquals(0, cardsWithCategories.size)
//    }
//
//    @Test
//    fun checkGetCardsWithCategoriesCardWithMultipleCategoriesCrosses() = runTest {
////        val cardsWithCategoriesDeferred = backgroundScope.async {
////            bankCardsCashbackCategoriesDao.getCardsWithCategories().first()
////        }
//
//        val size = 5
//        val cashbackCategoryEntities = generateCashbackCategoriesEntities(2)
//        val bankCardEntities = generateBankCardsEntities(size)
//        val bankCardsCashbackCategoriesXRefs =
//            List(bankCardEntities.size * cashbackCategoryEntities.size) { index ->
//                BankCardCashbackCategoryXRefEntity(
//                    bankCardName = bankCardEntities[index / cashbackCategoryEntities.size].name,
//                    cashbackCategoryName = cashbackCategoryEntities[index % cashbackCategoryEntities.size].name,
//                    isSelected = false,
//                    percent = 10f * index
//                )
//            }
//        bankCardsCashbackCategoriesDao.insertCategories(cashbackCategoryEntities)
//        bankCardsCashbackCategoriesDao.insertCards(bankCardEntities)
//        bankCardsCashbackCategoriesDao.insertCardsCategoriesXRefs(bankCardsCashbackCategoriesXRefs)
//
////        val cardsWithCategories = cardsWithCategoriesDeferred.await()
//        val cardsWithCategories = bankCardsCashbackCategoriesDao.getCardsWithCategories()
//        Assert.assertEquals(
//            bankCardEntities.size * cashbackCategoryEntities.size,
//            cardsWithCategories.size
//        )
//        cardsWithCategories.forEachIndexed { index, cardWithCategories ->
//            Assert.assertEquals(
//                cashbackCategoryEntities[index % cashbackCategoryEntities.size].name,
//                cardWithCategories.cashbackCategoryName
//            )
//            Assert.assertEquals(
//                bankCardEntities[index / cashbackCategoryEntities.size].name,
//                cardWithCategories.bankCardName
//            )
//            Assert.assertEquals(
//                bankCardsCashbackCategoriesXRefs[index].percent,
//                cardWithCategories.percent
//            )
//        }
//    }

    private fun generateBankCardsEntities(size: Int): List<BankCardEntity> = List(size) { index ->
        BankCardEntity(name = "BankCard $index", order = index)
    }

    private fun generateCashbackCategoriesEntities(size: Int): List<CashbackCategoryEntity> =
        List(size) { index ->
            CashbackCategoryEntity(name = "Cashback Category $index")
        }
}