package com.elli0tt.cashback_helper.data.mapper

import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryXRefEntity
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardCashbackCategoryXRef

fun BankCardEntity.toBankCard() = BankCard(
    name = this.name,
    order = this.order,
    maxSelectedCategoriesCount = this.maxSelectedCategoriesCount
)

fun BankCard.toBankCardEntity() = BankCardEntity(
    name = this.name,
    order = this.order,
    maxSelectedCategoriesCount = this.maxSelectedCategoriesCount
)

fun List<BankCardEntity>.toBankCardsList(): List<BankCard> = this.map { it.toBankCard() }

fun List<BankCard>.toBankCardsEntitiesList(): List<BankCardEntity> =
    this.map { it.toBankCardEntity() }

//fun BankCardWithCashbackCategoriesView.toBankCardWithCashbackCategories() =
//    BankCardWithCashbackCategories(
//        bankCard = this.bankCardEntity.toBankCard(),
////        cashbackCategories = this.cashbackCategoriesEntities.toCashbackCategoriesList()
//        cashbackCategories = emptyList()
//    )
//
//fun BankCardWithCashbackCategories.toBankCardWithCashbackCategoriesView() =
//    BankCardWithCashbackCategoriesView(
//        bankCardEntity = this.bankCard.toBankCardEntity(),
////        cashbackCategoriesEntities = this.cashbackCategories.toCashbackCategoryEntitiesList()
//        cashbackCategoriesEntities = emptyList()
//    )
//
//fun List<BankCardWithCashbackCategoriesView>.toBankCardsWithCashbackCategoriesList(): List<BankCardWithCashbackCategories> =
//    this.map { it.toBankCardWithCashbackCategories() }

fun BankCardCashbackCategoryXRef.toBankCardCashbackCategoryCrossRefEntity() =
    BankCardCashbackCategoryXRefEntity(
        bankCardName = this.bankCardName,
        cashbackCategoryName = this.cashbackCategoryName,
        isSelected = this.isSelected,
        percent = this.percent,
    )

fun BankCardCashbackCategoryXRefEntity.toBankCardCashbackCategoryCrossRef() =
    BankCardCashbackCategoryXRef(
        bankCardName = this.bankCardName,
        cashbackCategoryName = this.cashbackCategoryName,
        isSelected = this.isSelected,
        percent = this.percent,
    )

fun List<BankCardCashbackCategoryXRefEntity>.toBankCardsCashbackCategoriesCrossRefsList(): List<BankCardCashbackCategoryXRef> =
    this.map { it.toBankCardCashbackCategoryCrossRef() }