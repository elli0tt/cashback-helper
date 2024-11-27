package com.elli0tt.cashback_helper.domain.use_case

import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithPercent

interface GetCashbackCategoriesFromImageUseCase {
    suspend operator fun invoke(imageUri: String): List<CashbackCategoryWithPercent>
}