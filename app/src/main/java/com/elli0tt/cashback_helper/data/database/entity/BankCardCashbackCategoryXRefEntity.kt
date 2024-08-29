package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryCrossRefEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [BankCardEntity.COLUMN_NAME, CashbackCategoryEntity.COLUMN_NAME]
)
data class BankCardCashbackCategoryCrossRefEntity(
    @ColumnInfo(name = BankCardEntity.COLUMN_NAME) val bankCardName: String,
    @ColumnInfo(name = CashbackCategoryEntity.COLUMN_NAME) val cashbackCategoryName: String,
    @ColumnInfo(name = COLUMN_IS_SELECTED) val isSelected: Boolean,
    @ColumnInfo(name = COLUMN_PERCENT) val percent: Float,
) {
    companion object {
        const val TABLE_NAME = "bank_cards_cashback_categories_cross_ref_table"
        const val COLUMN_IS_SELECTED = "is_selected"
        const val COLUMN_PERCENT = "percent"
    }
}
