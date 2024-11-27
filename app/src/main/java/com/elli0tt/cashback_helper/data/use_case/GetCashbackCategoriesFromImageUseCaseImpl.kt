package com.elli0tt.cashback_helper.data.use_case

import android.util.Log
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithPercent
import com.elli0tt.cashback_helper.domain.model.RecognizedText
import com.elli0tt.cashback_helper.domain.use_case.GetCashbackCategoriesFromImageUseCase
import com.elli0tt.cashback_helper.domain.use_case.RecognizeTextUseCase
import org.koin.core.annotation.Single

@Single
class GetCashbackCategoriesFromImageUseCaseImpl(
    private val recognizeTextUseCase: RecognizeTextUseCase
) : GetCashbackCategoriesFromImageUseCase {

    override suspend fun invoke(imageUri: String): List<CashbackCategoryWithPercent> {
        val cashbackCategories = recognizeTextUseCase(imageUri).extractCashbackCategories()
        Log.d(
            TAG,
            "getCashbackCategoriesFromImageUseCaseImpl(): " +
                    "imageUri: $imageUri, " +
                    "cashbackCategories: ${cashbackCategories.joinToString(separator = "\n")}"
        )
        return cashbackCategories
    }

    private fun RecognizedText.extractCashbackCategories(): List<CashbackCategoryWithPercent> {
        return when (this) {
            is RecognizedText.Failure -> emptyList()
            is RecognizedText.Success -> this.extractCashbackCategories()
        }
    }

    private fun RecognizedText.Success.extractCashbackCategories(): List<CashbackCategoryWithPercent> {
        return this.text
            .split("\n")
            .filter { it.contains("%") }
            .map { categoryString ->
                CashbackCategoryWithPercent(
                    name = categoryString.substringAfter("%").trim(),
                    percent = categoryString.substringBefore("%")
                        .trim()
                        .replace(',', '.')
                        .toFloat()
                )
            }
    }

    companion object {
        private const val TAG = "GetCashbackCategoriesFromImageUseCaseImpl"
    }
}