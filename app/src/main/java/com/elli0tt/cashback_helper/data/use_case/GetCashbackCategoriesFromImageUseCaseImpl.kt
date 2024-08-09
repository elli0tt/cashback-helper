package com.elli0tt.cashback_helper.data.use_case

import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.model.RecognizedText
import com.elli0tt.cashback_helper.domain.use_case.GetCashbackCategoriesFromImageUseCase
import com.elli0tt.cashback_helper.domain.use_case.RecognizeTextUseCase
import org.koin.core.annotation.Single

@Single
class GetCashbackCategoriesFromImageUseCaseImpl(
    private val recognizeTextUseCase: RecognizeTextUseCase
) : GetCashbackCategoriesFromImageUseCase {

    override suspend fun invoke(imageUri: String): List<CashbackCategory> {
        return recognizeTextUseCase(imageUri).extractCashbackCategories()
    }

    private fun RecognizedText.extractCashbackCategories(): List<CashbackCategory> {
        return when (this) {
            is RecognizedText.Failure -> emptyList()
            is RecognizedText.Success -> this.extractCashbackCategories()
        }
    }

    private fun RecognizedText.Success.extractCashbackCategories(): List<CashbackCategory> {
        return this.text
            .split("\n")
            .filter { it.contains("%") }
            .map { categoryString ->
                CashbackCategory(
                    name = categoryString.substringAfter("%").trim(),
                    percent = categoryString.substringBefore("%")
                        .trim()
                        .replace(',', '.')
                        .toFloat()
                )
            }
    }
}