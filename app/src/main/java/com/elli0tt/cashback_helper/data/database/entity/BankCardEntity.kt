package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity.Companion.COLUMN_NAME

@Entity(tableName = BankCardEntity.TABLE_NAME)
data class BankCardEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_ORDER) val order: Int
) {

    companion object {
        const val TABLE_NAME = "bank_cards_table"

        const val COLUMN_NAME = "bank_card_name"
        const val COLUMN_ORDER = "order_column"
    }
}