package com.elli0tt.cashback_helper.ui.screen.cashback.add_card

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.mapper.onlyNames
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithPercent
import com.elli0tt.cashback_helper.domain.repo.BankCardsCashbackCategoriesRepo
import com.elli0tt.cashback_helper.domain.use_case.GetCashbackCategoriesFromImageUseCase
import com.elli0tt.cashback_helper.domain.utils.CashbackPercentFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddBankCardWithCashbackCategoriesViewModel(
    private val getCashbackCategoriesFromImageUseCase: GetCashbackCategoriesFromImageUseCase,
    private val bankCardsCashbackCategoriesRepo: BankCardsCashbackCategoriesRepo
) : ViewModel() {

    private val availableBankCards: StateFlow<List<BankCard>> =
        bankCardsCashbackCategoriesRepo.getAllBankCards()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
            )

    val availableBankCardsNames: Flow<List<String>> = availableBankCards.onlyNames()

    private val _selectedBankCardIndex = MutableStateFlow(0)
    val selectedBankCardIndex: StateFlow<Int> = _selectedBankCardIndex.asStateFlow()

    private val _cashbackCategories = MutableStateFlow(emptyList<CashbackCategoryWithPercent>())
    val cashbackCategories: Flow<List<String>> = _cashbackCategories.formatted()

//    private val _newBankCardName = MutableStateFlow("")
//    val newBankCardName: StateFlow<String> = _newBankCardName.asStateFlow()

    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

    fun selectBankCard(index: Int) {
        _selectedBankCardIndex.value = index
    }

    fun recognizeText(imageUri: String) {
        Log.d(TAG, "recognizeText(): imageUri: $imageUri")
        viewModelScope.launch {
            _showLoading.value = true
            val cashbackCategories = getCashbackCategoriesFromImageUseCase(imageUri)
            _cashbackCategories.value = cashbackCategories
            _showLoading.value = false
        }
    }

//    fun onNewBankCardNameInputChanged(newBankCardName: String) {
//        _newBankCardName.value = newBankCardName
//    }

    fun saveBankCardWithCashbackCategories() = viewModelScope.launch {
        availableBankCards.value
            .getOrNull(selectedBankCardIndex.value)
            ?.let { selectedBankCard ->
                _showLoading.value = true
                bankCardsCashbackCategoriesRepo.addBankCardWithCashbackCategories(
                    BankCardWithCashbackCategories(selectedBankCard, _cashbackCategories.value)
                )
                _showLoading.value = false
            }
    }

    private fun StateFlow<List<CashbackCategoryWithPercent>>.formatted(): Flow<List<String>> =
        this.map { cashbackCategories ->
            cashbackCategories.map { category ->
                "${CashbackPercentFormatter.format(category.percent)} ${category.name}"
            }
        }

    companion object {
        private const val TAG = "AddBankCardWithCashbackCategoriesViewModel"
    }
}