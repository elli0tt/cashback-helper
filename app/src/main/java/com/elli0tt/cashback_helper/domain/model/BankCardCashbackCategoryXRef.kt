package com.elli0tt.cashback_helper.domain.model

data class BankCardCashbackCategoryXRef(
    val bankCardName: String,
    val cashbackCategoryName: String,
    val isSelected: Boolean,
    val percent: Float
)