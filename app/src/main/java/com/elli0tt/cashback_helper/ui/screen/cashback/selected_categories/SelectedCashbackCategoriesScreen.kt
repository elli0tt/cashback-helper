package com.elli0tt.cashback_helper.ui.screen.cashback.selected_categories

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elli0tt.cashback_helper.ui.common.FullscreenLoading
import com.elli0tt.cashback_helper.ui.theme.CashbackHelperTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectedCashbackCategoriesRoute(
    viewModel: SelectedCashbackCategoriesViewModel = koinViewModel()
) {
//    SelectedCashbackCategoriesScreen(viewModel.uiState)
    SelectedCashbackCategoriesScreen(SelectedCashbackCategoriesUiState.Loading)

}

@Composable
private fun SelectedCashbackCategoriesScreen(
    uiState: SelectedCashbackCategoriesUiState
) {
    Scaffold { innerPadding ->
        when (uiState) {
            SelectedCashbackCategoriesUiState.Loading -> {
                FullscreenLoading()
            }

            is SelectedCashbackCategoriesUiState.Loaded -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                        .fillMaxSize()
                ) {
                    items(uiState.cashbackCategories) { cashbackCategoryWithBankCards ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = cashbackCategoryWithBankCards.name)
                            Text(text = cashbackCategoryWithBankCards.bankCards.joinToString())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectedCashbackCategoriesScreenLoadingPreview() {
    CashbackHelperTheme {
        SelectedCashbackCategoriesScreen(uiState = SelectedCashbackCategoriesUiState.Loading)
    }
}

@Preview
@Composable
private fun SelectedCashbackCategoriesScreenLoadedPreview() {
    CashbackHelperTheme {
        SelectedCashbackCategoriesScreen(uiState = SelectedCashbackCategoriesUiState.Loading)
    }
}