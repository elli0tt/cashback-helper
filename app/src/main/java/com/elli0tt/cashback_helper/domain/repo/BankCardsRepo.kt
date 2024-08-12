package com.elli0tt.cashback_helper.domain.repo

import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import kotlinx.coroutines.flow.Flow

interface BankCardsRepo {
    suspend fun addBankCard(bankCard: BankCard)

    suspend fun addBankCards(bankCards: List<BankCard>)

    fun getAllBankCards(): Flow<List<BankCard>>

    fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategories>>
}