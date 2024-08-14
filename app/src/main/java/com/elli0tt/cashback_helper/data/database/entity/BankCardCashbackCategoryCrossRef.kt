package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = [BankCardEntity.COLUMN_NAME, CashbackCategoryEntity.COLUMN_NAME])
data class BankCardCashbackCategoryCrossRef(
    @ColumnInfo(name = BankCardEntity.COLUMN_NAME) val bankCardName: String,
    @ColumnInfo(name = CashbackCategoryEntity.COLUMN_NAME) val cashbackCategoryName: String
)
