package com.elli0tt.cashback_helper.ui.screen.cashback.add_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import com.elli0tt.cashback_helper.domain.use_case.GetCashbackCategoriesFromImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddBankCardWithCashbackCategoriesViewModel(
    private val getCashbackCategoriesFromImageUseCase: GetCashbackCategoriesFromImageUseCase,
    private val bankCardsRepo: BankCardsRepo
) : ViewModel() {

    private val _cashbackCategories = MutableStateFlow(emptyList<CashbackCategory>())
    val cashbackCategories: StateFlow<List<CashbackCategory>> = _cashbackCategories.asStateFlow()

    private val _cardName = MutableStateFlow("")
    val cardName: StateFlow<String> = _cardName.asStateFlow()

    fun recognizeText(imageUri: String) = viewModelScope.launch {
        val cashbackCategories = getCashbackCategoriesFromImageUseCase(imageUri)
        _cashbackCategories.value = cashbackCategories
    }

    fun onCardNameInputChanged(cardName: String) {
        _cardName.value = cardName
    }

    fun saveBankCardWithCashbackCategories() = viewModelScope.launch {
        bankCardsRepo.addBankCardWithCashbackCategories(
            BankCardWithCashbackCategories(
                bankCard = BankCard(name = cardName.value),
                cashbackCategories = cashbackCategories.value
            )
        )
    }
}