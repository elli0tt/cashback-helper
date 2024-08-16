package com.elli0tt.cashback_helper.ui.screen.cashback.saved_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SavedBankCardsViewModel(private val bankCardsRepo: BankCardsRepo): ViewModel() {

    val bankCardsList: Flow<List<String>> = bankCardsRepo.getAllBankCards().onlyNames()

    private val _bankCardName = MutableStateFlow("")
    val bankCardName: StateFlow<String> = _bankCardName.asStateFlow()

    private fun Flow<List<BankCard>>.onlyNames(): Flow<List<String>> = this.map { bankCards ->
        bankCards.map { bankCard -> bankCard.name }
    }

    fun onBankCardInputChanged(cardName: String) {
        _bankCardName.value = cardName
    }

    fun saveBankCard() = viewModelScope.launch {
        bankCardsRepo.addBankCard(BankCard(name = bankCardName.value))
    }
}