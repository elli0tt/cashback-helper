package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = BankCardEntity.TABLE_NAME)
data class BankCardEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_ORDER) val order: Int,
    @ColumnInfo(name = COLUMN_MAX_SELECTED_CATEGORIES_COUNT) val maxSelectedCategoriesCount: Int
) {

    companion object {
        const val TABLE_NAME = "bank_cards_table"

        const val COLUMN_NAME = "bank_card_name"
        const val COLUMN_ORDER = "order_column"
        const val COLUMN_MAX_SELECTED_CATEGORIES_COUNT = "max_selected_categories_count"
    }
}