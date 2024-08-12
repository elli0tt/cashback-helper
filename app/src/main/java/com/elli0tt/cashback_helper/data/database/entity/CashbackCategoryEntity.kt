package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CashbackCategoryEntity.TABLE_NAME)
data class CashbackCategoryEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_PERCENT) val percent: Float
) {
    companion object {
        const val TABLE_NAME = "cashback_categories_table"

        const val COLUMN_NAME = "name"
        const val COLUMN_PERCENT = "percent"
    }
}