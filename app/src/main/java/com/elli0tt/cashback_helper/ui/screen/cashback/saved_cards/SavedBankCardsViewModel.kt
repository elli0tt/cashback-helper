package com.elli0tt.cashback_helper.ui.screen.cashback.saved_cards

import androidx.lifecycle.ViewModel
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SavedBankCardsViewModel(bankCardsRepo: BankCardsRepo): ViewModel() {

    val bankCardsList: Flow<List<String>> = bankCardsRepo.getAllBankCards().onlyNames()

    private fun Flow<List<BankCard>>.onlyNames(): Flow<List<String>> = this.map { bankCards ->
        bankCards.map { bankCard -> bankCard.name }
    }
}