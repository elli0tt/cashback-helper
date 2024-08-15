package com.elli0tt.cashback_helper.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.elli0tt.cashback_helper.ui.App
import com.elli0tt.cashback_helper.ui.screen.cashback.add_card.AddBankCardWithCashbackCategoriesScreen
import com.elli0tt.cashback_helper.ui.theme.CashbackHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashbackHelperTheme {
                App()
            }
        }
    }
}