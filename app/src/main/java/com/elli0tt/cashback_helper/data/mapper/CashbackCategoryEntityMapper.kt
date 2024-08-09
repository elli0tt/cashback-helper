package com.elli0tt.cashback_helper.data.mapper

import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
import com.elli0tt.cashback_helper.domain.model.CashbackCategory

fun CashbackCategoryEntity.toCashbackCategory() =
    CashbackCategory(name = this.name, percent = this.percent)

fun CashbackCategory.toCashbackCategoryEntity() =
    CashbackCategoryEntity(name = this.name, percent = this.percent)

fun List<CashbackCategoryEntity>.toCashbackCategoriesList() = this.map { it.toCashbackCategory() }

fun List<CashbackCategory>.toCashbackCategoryEntitiesList() =
    this.map { it.toCashbackCategoryEntity() }
