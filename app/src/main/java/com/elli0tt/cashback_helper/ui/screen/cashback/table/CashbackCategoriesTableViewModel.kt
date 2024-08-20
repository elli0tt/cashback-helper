package com.elli0tt.cashback_helper.ui.screen.cashback.table

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.mapper.onlyNames
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardCashbackCategoryCrossRef
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import com.elli0tt.cashback_helper.domain.repo.CashbackCategoriesRepo
import com.elli0tt.cashback_helper.domain.utils.CashbackPercentFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CashbackCategoriesTableViewModel(
    private val bankCardsRepo: BankCardsRepo,
    cashbackCategoriesRepo: CashbackCategoriesRepo
) : ViewModel() {

    val bankCardsNamesList: StateFlow<List<String>> = bankCardsRepo.getAllBankCards()
        .onlyNames()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val cashbackCategoriesList: Flow<List<CashbackCategory>> =
        cashbackCategoriesRepo.getAllCategories()
    val cashbackCategoriesNames: StateFlow<List<String>> = cashbackCategoriesList
        .onlyNames()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val cashbackCategoriesTable: StateFlow<List<List<CashbackCategoryUiState>>> =
        combine(
            bankCardsRepo.getBankCardsWithCashbackCategories(),
            cashbackCategoriesList,
            bankCardsRepo.getAllBankCardsCashbackCategoriesCrossRefs()
        ) { bankCardsWithCashbackCategories, allCashbackCategories, bankCardsCashbackCategoriesCrossRefs ->
            Log.d(TAG, "cashbackCategoriesTable - update")
            val resultTable = mutableListOf<List<CashbackCategoryUiState>>()

            repeat(bankCardsWithCashbackCategories.size) { bankCardIndex ->
                resultTable += List(size = allCashbackCategories.size) { cashbackCategoryIndex ->
                    val cashbackCategory = allCashbackCategories[cashbackCategoryIndex]
                    val bankCardWithCashbackCategories =
                        bankCardsWithCashbackCategories[bankCardIndex]
                    if (bankCardWithCashbackCategories.cashbackCategories.map { it.name }
                            .contains(cashbackCategory.name)) {
                        CashbackCategoryUiState(
                            name = CashbackPercentFormatter.format(cashbackCategory.percent),
                            isSelected = bankCardsCashbackCategoriesCrossRefs.find {
                                it.bankCardName == bankCardWithCashbackCategories.bankCard.name &&
                                        it.cashbackCategoryName == cashbackCategory.name
                            }?.isSelected ?: false
                        )
                    } else {
                        CashbackCategoryUiState(name = "", isSelected = false)
                    }
                }
            }
            resultTable
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun selectCashbackCategory(
        bankCardIndex: Int,
        cashbackCategoryIndex: Int
    ) = viewModelScope.launch {
        if (bankCardIndex >= 0 && cashbackCategoryIndex >= 0) {
            val cashbackCategoryUiState =
                cashbackCategoriesTable.value[bankCardIndex][cashbackCategoryIndex]
            bankCardsRepo.selectCashbackCategory(
                BankCardCashbackCategoryCrossRef(
                    bankCardName = bankCardsNamesList.value[bankCardIndex],
                    cashbackCategoryName = cashbackCategoriesNames.value[cashbackCategoryIndex],
                    isSelected = !cashbackCategoryUiState.isSelected
                )
            )
        }
    }

    companion object {
        private const val TAG = "CashbackCategoriesTableViewModel"
    }
}

data class CashbackCategoryUiState(val name: String, val isSelected: Boolean)