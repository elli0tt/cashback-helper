package com.elli0tt.cashback_helper.domain.model

data class BankCardWithCashbackCategories(
    val bankCard: BankCard,
    val cashbackCategories: List<CashbackCategory>
)
