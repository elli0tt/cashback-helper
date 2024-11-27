package com.elli0tt.cashback_helper.ui.screen.cashback.selected_categories

sealed interface SelectedCashbackCategoriesUiState {
    data object Loading : SelectedCashbackCategoriesUiState
    data class Loaded(
        val cashbackCategories: List<SelectedCashbackCategory>
    ) : SelectedCashbackCategoriesUiState
}

data class SelectedCashbackCategory(val name: String, val bankCards: List<String>)