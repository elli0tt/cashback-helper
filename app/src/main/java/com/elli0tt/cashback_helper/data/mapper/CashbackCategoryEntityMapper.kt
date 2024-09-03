package com.elli0tt.cashback_helper.data.mapper

import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.model.CashbackCategory

fun CashbackCategoryEntity.toCashbackCategory() =
    CashbackCategory(name = this.name)

fun CashbackCategory.toCashbackCategoryEntity() =
    CashbackCategoryEntity(name = this.name)

fun List<CashbackCategoryEntity>.toCashbackCategoriesList(): List<CashbackCategory> =
    this.map { it.toCashbackCategory() }

@JvmName("cashbackCategoriesListToCashbackCategoryEntitiesList")
fun List<CashbackCategory>.toCashbackCategoryEntitiesList(): List<CashbackCategoryEntity> =
    this.map { it.toCashbackCategoryEntity() }

fun BankCardWithCashbackCategories.CashbackCategory.toCashbackCategoryEntity() =
    CashbackCategoryEntity(name = this.name)

@JvmName("bankCardWithCashbackCategoriesCashbackCategoryListToCashbackCategoryEntitiesList")
fun List<BankCardWithCashbackCategories.CashbackCategory>.toCashbackCategoryEntitiesList(): List<CashbackCategoryEntity> =
    this.map { it.toCashbackCategoryEntity() }

//fun CashbackCategoryWithBankCardsView.toCashbackCategoryWithBankCards() =
//    CashbackCategoryWithBankCards(
//        name = this.cashbackCategoryEntity.name,
//        bankCards = this.bankCardsEntities.
//    )
