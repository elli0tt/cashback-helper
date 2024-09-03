package com.elli0tt.cashback_helper.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elli0tt.cashback_helper.R

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun FullscreenLoading() {
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