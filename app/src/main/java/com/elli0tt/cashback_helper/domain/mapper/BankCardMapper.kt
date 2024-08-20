package com.elli0tt.cashback_helper.domain.mapper

import com.elli0tt.cashback_helper.domain.model.BankCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<BankCard>>.onlyNames(): Flow<List<String>> = this.map { bankCards ->
    bankCards.map { bankCard -> bankCard.name }
}