package com.elli0tt.cashback_helper.domain.model

data class BankCardCashbackCategoryCrossRef(
    val bankCardName: String,
    val cashbackCategoryName: String,
    val isSelected: Boolean
)