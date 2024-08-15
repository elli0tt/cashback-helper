package com.elli0tt.cashback_helper.ui.screen.cashback.add_card

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elli0tt.cashback_helper.R
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import org.koin.androidx.compose.koinViewModel

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
    val cardName: String by viewModel.cardName.collectAsState()
    val cashbackCategories: List<CashbackCategory> by viewModel.cashbackCategories.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = cardName,
                onValueChange = { viewModel.onCardNameInputChanged(cardName = it) },
                label = { Text(text = stringResource(R.string.card_name_label)) }
            )
            Button(
                onClick = {
                    pickPhotoLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
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
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(cashbackCategories) { cashbackCategory ->
                    Text(text = "${cashbackCategory.percent}% ${cashbackCategory.name}")
                }
            }
            Button(onClick = {
                viewModel.saveBankCardWithCashbackCategories()
                onNavigateBack()
            }) {
                Text(text = stringResource(R.string.cashback_categories_table_save))
            }
        }
    }
}