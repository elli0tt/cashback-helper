package com.elli0tt.cashback_helper.ui.screen.cashback.selected_categories

import androidx.lifecycle.ViewModel
import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithBankCards
import com.elli0tt.cashback_helper.domain.repo.BankCardsCashbackCategoriesRepo
import com.elli0tt.cashback_helper.domain.utils.CashbackPercentFormatter
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SelectedCashbackCategoriesViewModel(
    bankCardsCashbackCategoriesRepo: BankCardsCashbackCategoriesRepo,
) : ViewModel() {
    val uiState = bankCardsCashbackCategoriesRepo
        .getSelectedCashbackCategoriesWithBankCards()
        .map { selectedCashbackCategoriesWithBankCards ->
            SelectedCashbackCategoriesUiState.Loaded(
                selectedCashbackCategoriesWithBankCards.toSelectedCashbackCategoriesList()
            )
        }

    private fun List<CashbackCategoryWithBankCards>.toSelectedCashbackCategoriesList(): List<SelectedCashbackCategory> =
        map { cashbackCategoryWithBankCards ->
            SelectedCashbackCategory(
                name = cashbackCategoryWithBankCards.name,
                bankCards = cashbackCategoryWithBankCards.bankCards.formatted()
            )
        }

    private fun List<CashbackCategoryWithBankCards.BankCard>.formatted(): List<String> =
        map { bankCard ->
            "${bankCard.name}: ${CashbackPercentFormatter.format(bankCard.percent)}"
        }
}