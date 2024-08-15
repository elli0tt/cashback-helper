package com.elli0tt.cashback_helper.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import com.elli0tt.cashback_helper.domain.repo.CashbackCategoriesRepo
import com.elli0tt.cashback_helper.domain.use_case.GetCashbackCategoriesFromImageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getCashbackCategoriesFromImageUseCase: GetCashbackCategoriesFromImageUseCase,
    private val bankCardsRepo: BankCardsRepo,
    private val cashbackCategoriesRepo: CashbackCategoriesRepo
) : ViewModel() {

    private val _cashbackCategories = MutableStateFlow(emptyList<CashbackCategory>())
    val cashbackCategories: StateFlow<List<CashbackCategory>> = _cashbackCategories.asStateFlow()

    private val _cardName = MutableStateFlow("")
    val cardName: StateFlow<String> = _cardName.asStateFlow()

    val bankCardsNamesList: Flow<List<String>> = bankCardsRepo.getAllBankCards().onlyNames()

    private val cashbackCategoriesList: Flow<List<CashbackCategory>> = cashbackCategoriesRepo.getAllCategories()
    val cashbackCategoriesNames: Flow<List<String>> = cashbackCategoriesList.onlyNames()

    val cashbackCategoriesTable: Flow<List<List<String>>> =
        bankCardsRepo.getBankCardsWithCashbackCategories()
            .combine(cashbackCategoriesList) { bankCardsWithCashbackCategories, allCashbackCategories ->

                val resultTable = mutableListOf<List<String>>()

                repeat(bankCardsWithCashbackCategories.size) { bankCardIndex ->
                    resultTable += List(size = allCashbackCategories.size) { cashbackCategoryIndex ->
                        val cashbackCategory = allCashbackCategories[cashbackCategoryIndex]
                        if (bankCardsWithCashbackCategories[bankCardIndex].cashbackCategories.map { it.name }
                                .contains(cashbackCategory.name)) {
                            cashbackCategory.percent.toString() + "%"
                        } else {
                            ""
                        }
                    }
                }
                resultTable

//                mutableListOf()
//            bankCardsRepo.getBankCardsWithCashbackCategories()
//                .map { bankCardsWithCashbackCategories ->
//                    val cashbackCategoriesCount = cashbackCategoriesRepo.getCategoriesCount()
//                    val resultTable =
//                        ArrayList<List<String>>(initialCapacity = bankCardsWithCashbackCategories.size)
//
//                    repeat(bankCardsWithCashbackCategories.size) { bankCardIndex ->
//                        resultTable += List(size = cashbackCategoriesCount) { cashbackCategoryIndex ->
//                            ""
//                        }
//                    }
//                    resultTable
//                }
            }

    fun recognizeText(imageUri: String) {
        viewModelScope.launch {
            val cashbackCategories = getCashbackCategoriesFromImageUseCase(imageUri)
            _cashbackCategories.value = cashbackCategories
        }
    }

    fun onCardNameInputChanged(cardName: String) {
        _cardName.value = cardName
    }

    @JvmName("bankCardsNames")
    private fun Flow<List<BankCard>>.onlyNames(): Flow<List<String>> = this.map { bankCards ->
        bankCards.map { bankCard -> bankCard.name }
    }

    @JvmName("cashbackCategoriesNames")
    private fun Flow<List<CashbackCategory>>.onlyNames(): Flow<List<String>> =
        this.map { cashbackCategories ->
            cashbackCategories.map { cashbackCategory -> cashbackCategory.name }
        }
}
