package com.elli0tt.cashback_helper.ui.screen.cashback.selected_categories

import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithBankCards

sealed interface SelectedCashbackCategoriesUiState {
    data object Loading : SelectedCashbackCategoriesUiState
    data class Loaded(
        val cashbackCategories: List<CashbackCategoryWithBankCards>
    ) : SelectedCashbackCategoriesUiState
}