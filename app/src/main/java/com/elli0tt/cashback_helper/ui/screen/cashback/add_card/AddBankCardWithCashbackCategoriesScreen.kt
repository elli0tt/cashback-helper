package com.elli0tt.cashback_helper.ui.screen.cashback.add_card

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elli0tt.cashback_helper.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddBankCardWithCashbackCategoriesScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddBankCardWithCashbackCategoriesViewModel = koinViewModel()
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val pickPhotoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.recognizeText(uri.toString())
                imageUri = uri
            }
        }
    val availableBankCardsNames: List<String> by viewModel.availableBankCardsNames.collectAsState(
        initial = emptyList()
    )
    val selectedBankCardIndex: Int by viewModel.selectedBankCardIndex.collectAsState()
//    val newBankCardName: String by viewModel.newBankCardName.collectAsState()
    val cashbackCategories: List<String> by viewModel.cashbackCategories.collectAsState(initial = emptyList())
    val showLoading: Boolean by viewModel.showLoading.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(modifier = Modifier.selectableGroup()) {
                        availableBankCardsNames.forEachIndexed { index, bankCardName ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = index == selectedBankCardIndex,
                                    onClick = { viewModel.selectBankCard(index) })
                                Text(text = bankCardName)
                            }
                        }
                    }
                    // TODO add new card from here
//                OutlinedTextField(
//                    value = newBankCardName,
//                    onValueChange = { viewModel.onNewBankCardNameInputChanged(newBankCardName = it) },
//                    label = { Text(text = stringResource(R.string.add_bank_card_with_cashback_categories_card_name_label)) }
//                )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            pickPhotoLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ) {
                        Text(text = stringResource(R.string.add_bank_card_with_cashback_categories_choose_image_button))
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
                }
                items(cashbackCategories) { cashbackCategory ->
                    Text(text = cashbackCategory)
                }
                item {
                    Button(
                        modifier = Modifier.padding(vertical = 16.dp),
                        onClick = {
                            viewModel.saveBankCardWithCashbackCategories()
                                .invokeOnCompletion { onNavigateBack() }
                        }) {
                        Text(text = stringResource(R.string.cashback_categories_table_save_button_text))
                    }
                }
            }

            if (showLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInteropFilter { true }
                        .background(
                            color = colorResource(R.color.fullscreen_progress_back_background)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.width(64.dp))
                }
            }
        }
    }
}