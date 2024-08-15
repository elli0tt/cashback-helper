package com.elli0tt.cashback_helper.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elli0tt.cashback_helper.R
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.ui.App
import com.elli0tt.cashback_helper.ui.theme.CashbackHelperTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App {
                CashbackHelperTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                        ChoosePhotoScreen(padding = innerPadding)
                        CashbackCategoriesTableScreen(innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
fun ChoosePhotoScreen(
    padding: PaddingValues,
    mainViewModel: MainViewModel = koinViewModel()
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val pickPhotoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                mainViewModel.recognizeText(uri.toString())
                imageUri = uri
            }
        }
    val cardName: String by mainViewModel.cardName.collectAsState()
    val cashbackCategories: List<CashbackCategory> by mainViewModel.cashbackCategories.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                pickPhotoLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
        ) {
            Text(text = stringResource(R.string.choose_image_button))
        }
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Inside,
        )
        OutlinedTextField(
            value = cardName,
            onValueChange = { mainViewModel.onCardNameInputChanged(cardName = it) },
            label = { stringResource(R.string.card_name_label) }
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(cashbackCategories) { cashbackCategory ->
                Text(text = "${cashbackCategory.percent}% ${cashbackCategory.name}")
            }
        }
    }
}

@Composable
fun CashbackCategoriesTableScreen(
    padding: PaddingValues,
    mainViewModel: MainViewModel = koinViewModel()
) {
    val cashbackCategoriesTable: List<List<String>> by
    mainViewModel.cashbackCategoriesTable.collectAsState(initial = emptyList())
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(minSize = 10.dp),
//        modifier = Modifier
//            .padding(padding)
//            .fillMaxSize()
//            .verticalScroll(state = ScrollState(0))
//    ) {
//        items(cashbackCategoriesTable) { cashbackCategory ->
//            Text(text = cashbackCategory)
//        }
//    }

    val bankCards: List<String> by mainViewModel.bankCardsNamesList.collectAsState(initial = emptyList())
    val cashbackCategories: List<String> by mainViewModel.cashbackCategoriesNames.collectAsState(
        initial = emptyList()
    )

    Column {
        Table(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            rowModifier = Modifier
                .border(width = 1.dp, color = Color.Black),
            columnCount = bankCards.size + 1,
            rowCount = cashbackCategories.size + 1,
            cellContent = { columnIndex, rowIndex ->
                Text(
                    modifier = Modifier
//                    .border(width = 1.dp, color = Color.Black)
                        .padding(4.dp),
//                    .clickable {
//
//                    },
                    text = run {
                        when {
                            columnIndex == 0 && rowIndex == 0 -> ""
                            columnIndex == 0 -> cashbackCategories[rowIndex - 1]
                            rowIndex == 0 -> bankCards[columnIndex - 1]
                            else -> cashbackCategoriesTable[columnIndex - 1][rowIndex - 1]
                        }
                    }
                )
            }
        )

        CustomTable()
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