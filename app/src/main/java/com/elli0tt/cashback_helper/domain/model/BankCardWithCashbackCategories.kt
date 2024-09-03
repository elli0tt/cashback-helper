package com.elli0tt.cashback_helper.domain.model

data class BankCardWithCashbackCategories(
    val bankCard: BankCard,
    val cashbackCategories: List<CashbackCategory>
) {
    data class CashbackCategory(val name: String, val percent: Float)
}
