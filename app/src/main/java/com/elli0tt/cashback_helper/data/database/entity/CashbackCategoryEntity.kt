package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CashbackCategoryEntity.TABLE_NAME)
data class CashbackCategoryEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String
) {
    companion object {
        const val TABLE_NAME = "cashback_categories_table"

        const val COLUMN_NAME = "cashback_category_name"
    }
}