package com.elli0tt.cashback_helper.ui.screen.cashback.selected_categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elli0tt.cashback_helper.ui.common.FullscreenLoading
import com.elli0tt.cashback_helper.ui.theme.CashbackHelperTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectedCashbackCategoriesRoute(
    viewModel: SelectedCashbackCategoriesViewModel = koinViewModel()
) {
    val uiState: SelectedCashbackCategoriesUiState by viewModel.uiState.collectAsState(initial = SelectedCashbackCategoriesUiState.Loading)
    SelectedCashbackCategoriesScreen(uiState)
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
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    items(uiState.cashbackCategories) { cashbackCategoryWithBankCards ->
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = cashbackCategoryWithBankCards.name,
                                fontSize = 20.sp
                            )
                            Text(
                                text = cashbackCategoryWithBankCards.bankCards
                                    .joinToString(separator = "\n")
                            )
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