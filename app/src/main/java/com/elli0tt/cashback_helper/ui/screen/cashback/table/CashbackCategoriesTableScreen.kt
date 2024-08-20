package com.elli0tt.cashback_helper.ui.screen.cashback.table

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elli0tt.cashback_helper.R
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun CashbackCategoriesTableScreen(
    onNavigateToAddBankCardWithCashbackCategories: () -> Unit,
    viewModel: CashbackCategoriesTableViewModel = koinViewModel()
) {
    val cashbackCategoriesTable: List<List<CashbackCategoryUiState>> by
    viewModel.cashbackCategoriesTable.collectAsState(initial = emptyList())

    val bankCards: List<String> by viewModel.bankCardsNamesList.collectAsState(initial = emptyList())
    val cashbackCategories: List<String> by viewModel.cashbackCategoriesNames.collectAsState(
        initial = emptyList()
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cashbackCategoriesTable.isNotEmpty()) {
                Table(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    rowModifier = Modifier
                        .border(width = 1.dp, color = Color.Black),
                    columnCount = bankCards.size + 1,
                    rowCount = cashbackCategories.size + 1,
                    cellContent = { columnIndex, rowIndex ->
                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = if (columnIndex == 0 ||
                                        rowIndex == 0 ||
                                        !cashbackCategoriesTable[columnIndex - 1][rowIndex - 1].isSelected
                                    ) {
//                                        Color.Green
                                        Color.Transparent
                                    } else {
//                                        Color.Transparent
                                        Color.Green
                                    }
                                )
                                .clickable {
                                    viewModel.selectCashbackCategory(
                                        bankCardIndex = columnIndex - 1,
                                        cashbackCategoryIndex = rowIndex - 1
                                    )
                                },
                            text = run {
                                when {
                                    columnIndex == 0 && rowIndex == 0 -> ""
                                    columnIndex == 0 -> cashbackCategories[rowIndex - 1]
                                    rowIndex == 0 -> bankCards[columnIndex - 1]
                                    else -> cashbackCategoriesTable[columnIndex - 1][rowIndex - 1].name
                                }
                            }
                        )
                    }
                )
            }

            Button(
                onClick = onNavigateToAddBankCardWithCashbackCategories
            ) {
                Text(stringResource(id = R.string.cashback_categories_table_add_bank_card))
            }
        }
    }
}

@Composable
fun Table(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    verticalLazyListState: LazyListState = rememberLazyListState(),
    horizontalScrollState: ScrollState = rememberScrollState(),
    columnCount: Int,
    rowCount: Int,
    beforeRow: (@Composable (rowIndex: Int) -> Unit)? = null,
    afterRow: (@Composable (rowIndex: Int) -> Unit)? = null,
    cellContent: @Composable (columnIndex: Int, rowIndex: Int) -> Unit
) {
    val columnWidths = remember { mutableStateMapOf<Int, Int>() }

    Box(modifier = modifier.then(Modifier.horizontalScroll(horizontalScrollState))) {
        LazyColumn(state = verticalLazyListState) {
            items(rowCount) { rowIndex ->
                Column {
                    beforeRow?.invoke(rowIndex)

                    Row(modifier = rowModifier) {
                        (0 until columnCount).forEach { columnIndex ->
                            Box(modifier = Modifier
                                .layout { measurable, constraints ->
                                    val placeable = measurable.measure(constraints)

                                    val existingWidth = columnWidths[columnIndex] ?: 0
                                    val maxWidth = maxOf(existingWidth, placeable.width)

                                    if (maxWidth > existingWidth) {
                                        columnWidths[columnIndex] = maxWidth
                                    }

                                    layout(width = maxWidth, height = placeable.height) {
                                        placeable.placeRelative(0, 0)
                                    }
                                }
                                .clickable {

                                }
                            ) {
                                cellContent(columnIndex, rowIndex)
                            }
                        }
                    }

                    afterRow?.invoke(rowIndex)
                }
            }
        }
    }
}

@Composable
fun CustomTable() {
    val columnsCount = 10
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        LazyColumn {
            items(count = 10) { rowIndex ->
                Row {
                    repeat(columnsCount) { columnIndex ->
                        Text(
//                    modifier = Modifier
//                    .border(width = 1.dp, color = Color.Black)
//                        .fillMaxWidth()
//                        .padding(4.dp),
//                    .clickable {
//
//                    },
                            text = run {
                                when {
                                    columnIndex == 0 && rowIndex == 0 -> ""
                                    columnIndex == 0 -> "Cashback category $rowIndex"
                                    rowIndex == 0 -> "Bank card $columnIndex"
                                    else -> "${Random.nextInt(from = 1, until = 30)}%"
                                }
//                                "1"
                            }
                        )
                    }
                }
            }
        }
    }
}