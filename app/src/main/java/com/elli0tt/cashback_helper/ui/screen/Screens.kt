package com.elli0tt.cashback_helper.ui.screen

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object AddBankCardWithCashbackCategories : Screens()

    @Serializable
    data object CashbackCategoriesTable : Screens()

    @Serializable
    data object SavedBankCards: Screens()
}