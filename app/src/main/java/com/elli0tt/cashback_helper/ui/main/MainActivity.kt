package com.elli0tt.cashback_helper.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.elli0tt.cashback_helper.ui.App
import com.elli0tt.cashback_helper.ui.theme.CashbackHelperTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App {
                CashbackHelperTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        ChoosePhotoScreen(padding = innerPadding)
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