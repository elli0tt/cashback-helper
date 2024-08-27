package com.elli0tt.cashback_helper.data.repo

import android.util.Log
import com.elli0tt.cashback_helper.data.database.dao.BankCardsDao
import com.elli0tt.cashback_helper.data.database.dao.CashbackCategoriesDao
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryCrossRefEntity
import com.elli0tt.cashback_helper.data.mapper.toBankCardCashbackCategoryCrossRefEntity
import com.elli0tt.cashback_helper.data.mapper.toBankCardEntity
import com.elli0tt.cashback_helper.data.mapper.toBankCardsCashbackCategoriesCrossRefsList
import com.elli0tt.cashback_helper.data.mapper.toBankCardsEntitiesList
import com.elli0tt.cashback_helper.data.mapper.toBankCardsList
import com.elli0tt.cashback_helper.data.mapper.toBankCardsWithCashbackCategoriesList
import com.elli0tt.cashback_helper.data.mapper.toCashbackCategoryEntitiesList
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardCashbackCategoryCrossRef
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class BankCardsRepoImpl(
    private val bankCardsDao: BankCardsDao,
    private val cashbackCategoriesDao: CashbackCategoriesDao
) : BankCardsRepo {
    override suspend fun addBankCard(bankCard: BankCard) {
        Log.d(TAG, "addBankCard(): $bankCard")
        bankCardsDao.insertBankCard(bankCard.toBankCardEntity())
    }

    override suspend fun addBankCards(bankCards: List<BankCard>) {
        Log.d(TAG, "addBankCards(): ${bankCards.joinToString()}")
        bankCardsDao.addBankCards(bankCards.toBankCardsEntitiesList())
    }

    override suspend fun addBankCardWithCashbackCategories(
        bankCardWithCashbackCategories: BankCardWithCashbackCategories
    ) {
        Log.d(TAG, "addBankCardWithCashbackCategories(): $bankCardWithCashbackCategories")
        bankCardsDao.insertBankCard(bankCardWithCashbackCategories.bankCard.toBankCardEntity())
        cashbackCategoriesDao.insertCategories(
            bankCardWithCashbackCategories.cashbackCategories.toCashbackCategoryEntitiesList()
        )
        bankCardWithCashbackCategories.cashbackCategories.forEach { cashbackCategory ->
            bankCardsDao.insertBankCardCashbackCategoryCrossRef(
                BankCardCashbackCategoryCrossRefEntity(
                    bankCardName = bankCardWithCashbackCategories.bankCard.name,
                    cashbackCategoryName = cashbackCategory.name,
                    isSelected = false
                )
            )
        }
    }

    override fun getAllBankCards(): Flow<List<BankCard>> {
        return bankCardsDao.getAllBankCards().map { bankCardsEntities ->
            Log.d(TAG, "getAllBankCards(): size: ${bankCardsEntities.size}")
            bankCardsEntities.toBankCardsList()
        }
    }

    override fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategories>> {
        return bankCardsDao.getBankCardsWithCashbackCategories()
            .map { bankCardsWithCashbackCategories ->
                Log.d(
                    TAG,
                    "getBankCardsWithCashbackCategories(): " +
                            "size: ${bankCardsWithCashbackCategories.size}"
                )
                bankCardsWithCashbackCategories.toBankCardsWithCashbackCategoriesList()
            }
    }

    override suspend fun selectCashbackCategory(
        bankCardCashbackCategoryCrossRef: BankCardCashbackCategoryCrossRef
    ) {
        Log.d(TAG, "selectCashbackCategory(): $bankCardCashbackCategoryCrossRef")
        bankCardsDao.updateBankCardCashbackCategoryCrossRef(
            bankCardCashbackCategoryCrossRef.toBankCardCashbackCategoryCrossRefEntity()
        )
    }

    override fun getAllBankCardsCashbackCategoriesCrossRefs(): Flow<List<BankCardCashbackCategoryCrossRef>> {
        return bankCardsDao.getAllBankCardsCashbackCategoriesCrossRefs()
            .map { bankCardsCashbackCategoriesCrossRefs ->
                Log.d(
                    TAG,
                    "getAllBankCardsCashbackCategoriesCrossRefs(): " +
                            "size: ${bankCardsCashbackCategoriesCrossRefs.size}"
                )
                bankCardsCashbackCategoriesCrossRefs.toBankCardsCashbackCategoriesCrossRefsList()
            }
    }

    override suspend fun getBankCardsCount(): Int {
        val count = bankCardsDao.getBankCardsCount()
        Log.d(TAG, "getBankCardsCount(): $count")
        return count
    }

    companion object {
        private const val TAG = "BankCardsRepoImpl"
    }
}
