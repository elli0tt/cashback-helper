package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CashbackCategoryEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_PERCENT) val percent: Int
) {
    companion object {
        const val TABLE_NAME = "cashback_category_table"

        const val COLUMN_NAME = "name"
        const val COLUMN_PERCENT = "percent"
    }
}