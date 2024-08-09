package com.elli0tt.cashback_helper.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.use_case.GetCashbackCategoriesFromImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getCashbackCategoriesFromImageUseCase: GetCashbackCategoriesFromImageUseCase
) : ViewModel() {

    private val _cashbackCategories = MutableStateFlow(emptyList<CashbackCategory>())
    val cashbackCategories: StateFlow<List<CashbackCategory>> = _cashbackCategories.asStateFlow()

    fun recognizeText(imageUri: String) {
        viewModelScope.launch {
            val cashbackCategories = getCashbackCategoriesFromImageUseCase(imageUri)
            _cashbackCategories.value = cashbackCategories
        }
    }
}
