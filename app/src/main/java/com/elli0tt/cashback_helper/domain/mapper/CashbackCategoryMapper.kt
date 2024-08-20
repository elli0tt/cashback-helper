package com.elli0tt.cashback_helper.domain.mapper

import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<CashbackCategory>>.onlyNames(): Flow<List<String>> =
    this.map { cashbackCategories ->
        cashbackCategories.map { cashbackCategory -> cashbackCategory.name }
    }