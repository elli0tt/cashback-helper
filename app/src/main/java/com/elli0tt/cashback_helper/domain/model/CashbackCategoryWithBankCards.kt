package com.elli0tt.cashback_helper.domain.model

data class CashbackCategoryWithBankCards(val name: String, val bankCards: List<BankCard>) {
    data class BankCard(val name: String, val percent: Float)
}