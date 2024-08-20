package com.elli0tt.cashback_helper.ui.screen.cashback.debug_actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.BuildConfig
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DebugActionsViewModel(private val bankCardsRepo: BankCardsRepo) : ViewModel() {

    fun addMockBankCardsWithCashbackCategories() {

    }

    fun prefillBankCards() = viewModelScope.launch {
        bankCardsRepo.addBankCards(
            BuildConfig.PREDEFINED_BANK_CARDS.map { bankCardName ->
                BankCard(name = bankCardName)
            }
        )
    }
}