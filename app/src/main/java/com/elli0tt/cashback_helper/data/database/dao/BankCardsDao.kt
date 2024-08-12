package com.elli0tt.cashback_helper.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.view.BankCardWithCashbackCategoriesView
import kotlinx.coroutines.flow.Flow

@Dao
interface BankCardsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBankCard(bankCardEntity: BankCardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBankCards(bankCardsEntities: List<BankCardEntity>)

    @Query("SELECT * FROM ${BankCardEntity.TABLE_NAME}")
    fun getAllBankCards(): Flow<List<BankCardEntity>>

    @Transaction
    @Query("SELECT * FROM ${BankCardEntity.TABLE_NAME}")
    fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategoriesView>>
}