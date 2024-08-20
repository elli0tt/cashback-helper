package com.elli0tt.cashback_helper.data.database.view

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryCrossRefEntity
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity

data class BankCardWithCashbackCategoriesView(
    @Embedded val bankCardEntity: BankCardEntity,
    @Relation(
        parentColumn = BankCardEntity.COLUMN_NAME,
        entityColumn = CashbackCategoryEntity.COLUMN_NAME,
        associateBy = Junction(BankCardCashbackCategoryCrossRefEntity::class)
    )
    val cashbackCategoriesEntities: List<CashbackCategoryEntity>
)
