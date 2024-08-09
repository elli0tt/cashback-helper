package com.elli0tt.cashback_helper.ui

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun App(content: @Composable () -> Unit) {
    KoinAndroidContext {
        content()
    }
}