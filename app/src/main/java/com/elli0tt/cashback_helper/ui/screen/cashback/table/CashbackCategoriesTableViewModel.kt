package com.elli0tt.cashback_helper.ui.screen.cashback.table

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.mapper.onlyNames
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardCashbackCategoryXRef
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsCashbackCategoriesRepo
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
    private val bankCardsCashbackCategoriesRepo: BankCardsCashbackCategoriesRepo
) : ViewModel() {

    private val bankCardsList: Flow<List<BankCard>> =
        bankCardsCashbackCategoriesRepo.getAllBankCards()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
            )

    val bankCardsTitlesList: StateFlow<List<String>> =
        bankCardsList
            .map { bankCards ->
                bankCards.map { bankCard -> "${bankCard.name} (${bankCard.maxSelectedCategoriesCount})" }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
            )

    private val cashbackCategoriesList: Flow<List<CashbackCategory>> =
        bankCardsCashbackCategoriesRepo.getAllCategories()
    val cashbackCategoriesNames: StateFlow<List<String>> = cashbackCategoriesList
        .onlyNames()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val cashbackCategoriesTable: StateFlow<List<List<CashbackCategoryUiState>>> =
        combine(
            bankCardsList,
            cashbackCategoriesList,
            bankCardsCashbackCategoriesRepo.getAllBankCardsCashbackCategoriesCrossRefs()
        ) { bankCardsWithCashbackCategories, allCashbackCategories, bankCardsCashbackCategoriesCrossRefs ->
            Log.d(TAG, "cashbackCategoriesTable - update")
            buildCashbackCategoriesTable(
                bankCardsWithCashbackCategories,
                allCashbackCategories,
                bankCardsCashbackCategoriesCrossRefs
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private fun buildCashbackCategoriesTable(
        bankCards: List<BankCard>,
        cashbackCategories: List<CashbackCategory>,
        bankCardsCashbackCategoriesCrossRefs: List<BankCardCashbackCategoryXRef>
    ): List<List<CashbackCategoryUiState>> {
        Log.d(
            TAG,
            "buildCashbackCategoriesTable(): " +
                    "bankCards.size: ${bankCards.size}, " +
                    "cashbackCategories.size: ${cashbackCategories.size}"
        )
        val resultTable = mutableListOf<List<CashbackCategoryUiState>>()

        bankCards.forEach { bankCard ->
            resultTable += cashbackCategories.map { cashbackCategory ->
                val crossRef = bankCardsCashbackCategoriesCrossRefs.find { crossRef ->
                    crossRef.bankCardName == bankCard.name &&
                            crossRef.cashbackCategoryName == cashbackCategory.name
                }
                CashbackCategoryUiState(
                    name = crossRef?.let { CashbackPercentFormatter.format(crossRef.percent) }
                        ?: "",
                    isSelected = crossRef?.isSelected ?: false
                )
            }
        }
//        repeat(allBankCards.size) { bankCardIndex ->
//            resultTable += List(size = allCashbackCategories.size) { cashbackCategoryIndex ->
//                val cashbackCategory = allCashbackCategories[cashbackCategoryIndex]
//                val bankCardWithCashbackCategories = allBankCards[bankCardIndex]
//                if (bankCardWithCashbackCategories.cashbackCategories.map { it.name }
//                        .contains(cashbackCategory.name)) {
//                    CashbackCategoryUiState(
////                        name = CashbackPercentFormatter.format(cashbackCategory.percent),
//                        name = "stuuuuuuub",
//                        isSelected = bankCardsCashbackCategoriesCrossRefs.find {
//                            it.bankCardName == bankCardWithCashbackCategories.bankCard.name &&
//                                    it.cashbackCategoryName == cashbackCategory.name
//                        }?.isSelected ?: false
//                    )
//                } else {
//                    CashbackCategoryUiState(name = "", isSelected = false)
//                }
//            }
//        }
        return resultTable
    }

    fun selectCashbackCategory(
        bankCardIndex: Int,
        cashbackCategoryIndex: Int
    ) = viewModelScope.launch {
        if (bankCardIndex >= 0 && cashbackCategoryIndex >= 0) {
            val cashbackCategoryUiState =
                cashbackCategoriesTable.value[bankCardIndex][cashbackCategoryIndex]
            bankCardsCashbackCategoriesRepo.selectCashbackCategory(
                bankCardName = bankCardsTitlesList.value[bankCardIndex],
                cashbackCategoryName = cashbackCategoriesNames.value[cashbackCategoryIndex],
                isSelected = !cashbackCategoryUiState.isSelected
            )
        }
    }

    companion object {
        private const val TAG = "CashbackCategoriesTableViewModel"
    }
}

data class CashbackCategoryUiState(val name: String, val isSelected: Boolean)