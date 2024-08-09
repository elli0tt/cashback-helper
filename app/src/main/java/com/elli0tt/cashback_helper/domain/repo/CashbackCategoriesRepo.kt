package com.elli0tt.cashback_helper.domain.repo

import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import kotlinx.coroutines.flow.Flow

interface CashbackCategoriesRepo {
    suspend fun addCategory(cashbackCategory: CashbackCategory)

    suspend fun addCategories(cashbackCategories: List<CashbackCategory>)

    fun getAllCategories(): Flow<List<CashbackCategory>>
}