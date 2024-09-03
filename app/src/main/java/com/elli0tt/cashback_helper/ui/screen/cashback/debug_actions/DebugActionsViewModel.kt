package com.elli0tt.cashback_helper.ui.screen.cashback.debug_actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.BuildConfig
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.repo.BankCardsCashbackCategoriesRepo
import com.elli0tt.cashback_helper.domain.use_case.DebugAddMockBankCardsWithCashbackCategoriesUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DebugActionsViewModel(
    private val bankCardsCashbackCategoriesRepo: BankCardsCashbackCategoriesRepo,
    private val debugAddMockBankCardsWithCashbackCategoriesUseCase: DebugAddMockBankCardsWithCashbackCategoriesUseCase
) : ViewModel() {

    fun addMockBankCardsWithCashbackCategories() = viewModelScope.launch {
        debugAddMockBankCardsWithCashbackCategoriesUseCase()
    }

    fun prefillBankCards() = viewModelScope.launch {
        val existingBankCardsCount = bankCardsCashbackCategoriesRepo.getBankCardsCount()
        bankCardsCashbackCategoriesRepo.addBankCards(
            BuildConfig.PREDEFINED_BANK_CARDS.mapIndexed { index, bankCardName ->
                BankCard(name = bankCardName, existingBankCardsCount + index)
            }
        )
    }
}