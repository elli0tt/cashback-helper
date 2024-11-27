package com.elli0tt.cashback_helper.data.repo

import android.util.Log
import com.elli0tt.cashback_helper.data.database.dao.BankCardsCashbackCategoriesDao
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryXRefEntity
import com.elli0tt.cashback_helper.data.mapper.toBankCardCashbackCategoryCrossRefEntity
import com.elli0tt.cashback_helper.data.mapper.toBankCardEntity
import com.elli0tt.cashback_helper.data.mapper.toBankCardsCashbackCategoriesCrossRefsList
import com.elli0tt.cashback_helper.data.mapper.toBankCardsEntitiesList
import com.elli0tt.cashback_helper.data.mapper.toBankCardsList
import com.elli0tt.cashback_helper.data.mapper.toCashbackCategoriesList
import com.elli0tt.cashback_helper.data.mapper.toCashbackCategoryEntitiesList
import com.elli0tt.cashback_helper.data.mapper.toCashbackCategoryEntity
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardCashbackCategoryXRef
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithBankCards
import com.elli0tt.cashback_helper.domain.repo.BankCardsCashbackCategoriesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class BankCardsCashbackCategoriesRepoImpl(
    private val bankCardsCashbackCategoriesDao: BankCardsCashbackCategoriesDao
) : BankCardsCashbackCategoriesRepo {

    override suspend fun addCategory(cashbackCategory: CashbackCategory) {
        Log.d(TAG, "addCategory(): $cashbackCategory")
        bankCardsCashbackCategoriesDao.insertCategory(cashbackCategory.toCashbackCategoryEntity())
    }

    override suspend fun addCategories(cashbackCategories: List<CashbackCategory>) {
        Log.d(TAG, "addCategories(): ${cashbackCategories.joinToString()}")
        bankCardsCashbackCategoriesDao.insertCategories(cashbackCategories.toCashbackCategoryEntitiesList())
    }

    override suspend fun addBankCard(bankCard: BankCard) {
        Log.d(TAG, "addBankCard(): $bankCard")
        bankCardsCashbackCategoriesDao.insertCard(bankCard.toBankCardEntity())
    }

    override suspend fun addBankCards(bankCards: List<BankCard>) {
        Log.d(TAG, "addBankCards(): ${bankCards.joinToString()}")
        bankCardsCashbackCategoriesDao.insertCards(bankCards.toBankCardsEntitiesList())
    }

    override suspend fun addBankCardWithCashbackCategories(
        bankCardWithCashbackCategories: BankCardWithCashbackCategories
    ) {
        Log.d(TAG, "addBankCardWithCashbackCategories(): $bankCardWithCashbackCategories")
        bankCardsCashbackCategoriesDao.insertCard(bankCardWithCashbackCategories.bankCard.toBankCardEntity())
        bankCardsCashbackCategoriesDao.insertCategories(
            bankCardWithCashbackCategories.cashbackCategories.toCashbackCategoryEntitiesList()
        )
        bankCardWithCashbackCategories.cashbackCategories.forEach { cashbackCategory ->
            bankCardsCashbackCategoriesDao.insertCardCategoryXRef(
                BankCardCashbackCategoryXRefEntity(
                    bankCardName = bankCardWithCashbackCategories.bankCard.name,
                    cashbackCategoryName = cashbackCategory.name,
                    isSelected = false,
                    percent = cashbackCategory.percent
                )
            )
        }
    }

    override fun getAllCategories(): Flow<List<CashbackCategory>> {
        return bankCardsCashbackCategoriesDao.getAllCategories().map { cashbackCategoryEntities ->
            Log.d(TAG, "getAllCategories(): size: ${cashbackCategoryEntities.size}")
            cashbackCategoryEntities.toCashbackCategoriesList()
        }
    }

    override fun getAllBankCards(): Flow<List<BankCard>> {
        return bankCardsCashbackCategoriesDao.getAllCards().map { bankCardsEntities ->
            Log.d(TAG, "getAllBankCards(): size: ${bankCardsEntities.size}")
            bankCardsEntities.toBankCardsList()
        }
    }

//    override fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategories>> {
//        return bankCardsCashbackCategoriesDao.getCardsWithCategories()
//            .map { bankCardsWithCashbackCategories ->
//                Log.d(
//                    TAG,
//                    "getBankCardsWithCashbackCategories(): " +
//                            "size: ${bankCardsWithCashbackCategories.size}"
//                )
//                bankCardsWithCashbackCategories.toBankCardsWithCashbackCategoriesList()
//            }
//    }

    override fun getAllBankCardsCashbackCategoriesCrossRefs(): Flow<List<BankCardCashbackCategoryXRef>> {
        return bankCardsCashbackCategoriesDao.getAllCardsCategoriesXRefs()
            .map { bankCardsCashbackCategoriesCrossRefs ->
                Log.d(
                    TAG,
                    "getAllBankCardsCashbackCategoriesCrossRefs(): " +
                            "size: ${bankCardsCashbackCategoriesCrossRefs.size}"
                )
                bankCardsCashbackCategoriesCrossRefs.toBankCardsCashbackCategoriesCrossRefsList()
            }
    }

    override fun getSelectedCashbackCategoriesWithBankCards(): Flow<List<CashbackCategoryWithBankCards>> =
        bankCardsCashbackCategoriesDao.getSelectedCashbackCategoriesWithBankCards()
            .map { bankCardCashbackCategoryViews ->
                val cashbackCategoriesMap =
                    mutableMapOf<String, MutableList<CashbackCategoryWithBankCards.BankCard>>()
                bankCardCashbackCategoryViews.forEach { (bankCardName, cashbackCategoryName, percent) ->
                    val bankCard = CashbackCategoryWithBankCards.BankCard(bankCardName, percent)
                    if (cashbackCategoryName in cashbackCategoriesMap) {
                        cashbackCategoriesMap[cashbackCategoryName]!! += bankCard
                    } else {
                        cashbackCategoriesMap[cashbackCategoryName] = mutableListOf(bankCard)
                    }
                }
                val resultList = cashbackCategoriesMap.map { (cashbackCategoryName, bankCards) ->
                    CashbackCategoryWithBankCards(cashbackCategoryName, bankCards)
                }

                Log.d(
                    TAG,
                    "getSelectedCashbackCategoriesWithBankCards(): " +
                            "bankCardCashbackCategoryViews.size: ${bankCardCashbackCategoryViews.size}," +
                            "resultList.size: ${resultList.size}"
                )
                resultList
            }

    override suspend fun getCategoriesCount(): Int {
        val count = bankCardsCashbackCategoriesDao.getCategoriesCount()
        Log.d(TAG, "getCategoriesCount(): $count")
        return count
    }

    override suspend fun getBankCardsCount(): Int {
        val count = bankCardsCashbackCategoriesDao.getCardsCount()
        Log.d(TAG, "getBankCardsCount(): $count")
        return count
    }

    override suspend fun selectCashbackCategory(
        bankCardName: String,
        cashbackCategoryName: String,
        isSelected: Boolean
    ) {
        Log.d(
            TAG,
            "selectCashbackCategory(): " +
                    "bankCardName: $bankCardName, " +
                    "cashbackCategoryName: $cashbackCategoryName, " +
                    "isSelected: $isSelected"
        )
        bankCardsCashbackCategoriesDao.selectCashbackCategory(
            bankCardName,
            cashbackCategoryName,
            isSelected
        )
    }

    companion object {
        private const val TAG = "BankCardsCashbackCategoriesRepoImpl"
    }
}
