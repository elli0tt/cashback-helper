package com.elli0tt.cashback_helper.domain.repo

import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardCashbackCategoryCrossRef
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import kotlinx.coroutines.flow.Flow

interface BankCardsRepo {
    suspend fun addBankCard(bankCard: BankCard)

    suspend fun addBankCards(bankCards: List<BankCard>)

    suspend fun addBankCardWithCashbackCategories(
        bankCardWithCashbackCategories: BankCardWithCashbackCategories
    )

    fun getAllBankCards(): Flow<List<BankCard>>

    fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategories>>

    suspend fun selectCashbackCategory(
        bankCardCashbackCategoryCrossRef: BankCardCashbackCategoryCrossRef
    )

    fun getAllBankCardsCashbackCategoriesCrossRefs(): Flow<List<BankCardCashbackCategoryCrossRef>>

    suspend fun getBankCardsCount(): Int
}