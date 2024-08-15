package com.elli0tt.cashback_helper.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import com.elli0tt.cashback_helper.domain.repo.CashbackCategoriesRepo
import com.elli0tt.cashback_helper.domain.use_case.GetCashbackCategoriesFromImageUseCase
import com.elli0tt.cashback_helper.domain.utils.CashbackPercentFormatter
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
    private val getCashbackCategoriesFromImageUseCase: GetCashbackCategoriesFromImageUseCase
) : ViewModel() {

    private val _cashbackCategories = MutableStateFlow(emptyList<CashbackCategory>())
    val cashbackCategories: StateFlow<List<CashbackCategory>> = _cashbackCategories.asStateFlow()

    private val _cardName = MutableStateFlow("")
    val cardName: StateFlow<String> = _cardName.asStateFlow()

    fun recognizeText(imageUri: String) {
        viewModelScope.launch {
            val cashbackCategories = getCashbackCategoriesFromImageUseCase(imageUri)
            _cashbackCategories.value = cashbackCategories
        }
    }

    fun onCardNameInputChanged(cardName: String) {
        _cardName.value = cardName
    }
}
