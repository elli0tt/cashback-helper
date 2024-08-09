package com.elli0tt.cashback_helper.domain.model

sealed interface RecognizedText {
    data class Success(val text: String): RecognizedText
    data object Failure: RecognizedText
}