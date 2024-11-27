package com.elli0tt.cashback_helper.domain.repo

import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardCashbackCategoryXRef
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithBankCards
import kotlinx.coroutines.flow.Flow

interface BankCardsCashbackCategoriesRepo {
    suspend fun addCategory(cashbackCategory: CashbackCategory)

    suspend fun addCategories(cashbackCategories: List<CashbackCategory>)

    suspend fun addBankCard(bankCard: BankCard)

    suspend fun addBankCards(bankCards: List<BankCard>)

    suspend fun addBankCardWithCashbackCategories(
        bankCardWithCashbackCategories: BankCardWithCashbackCategories
    )

    fun getAllCategories(): Flow<List<CashbackCategory>>

    fun getAllBankCards(): Flow<List<BankCard>>

//    fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategories>>
//    fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategoriesNew>>

    fun getAllBankCardsCashbackCategoriesCrossRefs(): Flow<List<BankCardCashbackCategoryXRef>>

    fun getSelectedCashbackCategoriesWithBankCards(): Flow<List<CashbackCategoryWithBankCards>>

    suspend fun getCategoriesCount(): Int

    suspend fun getBankCardsCount(): Int

    suspend fun selectCashbackCategory(
        bankCardName: String,
        cashbackCategoryName: String,
        isSelected: Boolean
    )
}