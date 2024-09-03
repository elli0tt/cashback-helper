package com.elli0tt.cashback_helper.ui.screen.cashback.saved_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.mapper.onlyNames
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.repo.BankCardsCashbackCategoriesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SavedBankCardsViewModel(private val bankCardsCashbackCategoriesRepo: BankCardsCashbackCategoriesRepo) : ViewModel() {

    val bankCardsList: Flow<List<String>> = bankCardsCashbackCategoriesRepo.getAllBankCards().onlyNames()

    private val _bankCardName = MutableStateFlow("")
    val bankCardName: StateFlow<String> = _bankCardName.asStateFlow()

    fun onBankCardInputChanged(cardName: String) {
        _bankCardName.value = cardName
    }

    fun saveBankCard() = viewModelScope.launch {
        bankCardsCashbackCategoriesRepo.addBankCard(
            BankCard(name = bankCardName.value, order = bankCardsCashbackCategoriesRepo.getBankCardsCount())
        )
    }
}