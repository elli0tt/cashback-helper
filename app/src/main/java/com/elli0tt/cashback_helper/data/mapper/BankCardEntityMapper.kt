package com.elli0tt.cashback_helper.data.mapper

import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.view.BankCardWithCashbackCategoriesView
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories

fun BankCardEntity.toBankCard() = BankCard(name = this.name)

fun BankCard.toBankCardEntity() = BankCardEntity(name = this.name)

fun List<BankCardEntity>.toBankCardsList(): List<BankCard> = this.map { it.toBankCard() }

fun List<BankCard>.toBankCardsEntitiesList(): List<BankCardEntity> =
    this.map { it.toBankCardEntity() }

fun BankCardWithCashbackCategoriesView.toBankCardWithCashbackCategories() =
    BankCardWithCashbackCategories(
        bankCard = this.bankCardEntity.toBankCard(),
        cashbackCategories = this.cashbackCategoriesEntities.toCashbackCategoriesList()
    )

fun BankCardWithCashbackCategories.toBankCardWithCashbackCategoriesView() =
    BankCardWithCashbackCategoriesView(
        bankCardEntity = this.bankCard.toBankCardEntity(),
        cashbackCategoriesEntities = this.cashbackCategories.toCashbackCategoryEntitiesList()
    )

fun List<BankCardWithCashbackCategoriesView>.toBankCardsWithCashbackCategoriesList(): List<BankCardWithCashbackCategories> =
    this.map { it.toBankCardWithCashbackCategories() }
