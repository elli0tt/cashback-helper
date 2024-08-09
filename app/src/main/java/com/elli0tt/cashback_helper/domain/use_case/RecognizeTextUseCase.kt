package com.elli0tt.cashback_helper.domain.use_case

import com.elli0tt.cashback_helper.domain.model.RecognizedText

interface RecognizeTextUseCase {
    suspend operator fun invoke(imageUri: String): RecognizedText
}
