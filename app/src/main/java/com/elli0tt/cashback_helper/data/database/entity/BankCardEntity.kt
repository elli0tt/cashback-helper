package com.elli0tt.cashback_helper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = BankCardEntity.TABLE_NAME)
data class BankCardEntity(@PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String) {

    companion object {
        const val TABLE_NAME = "bank_cards_table"

        const val COLUMN_NAME = "name"
    }
}