package com.elli0tt.cashback_helper.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
import com.elli0tt.cashback_helper.data.database.view.BankCardWithCashbackCategoriesView
import com.elli0tt.cashback_helper.data.database.view.CashbackCategoryWithBankCardsView
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.model.CashbackCategoryWithBankCards
import kotlinx.coroutines.flow.Flow

@Dao
interface CashbackCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(cashbackCategoryEntity: CashbackCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(cashbackCategoryEntities: List<CashbackCategoryEntity>)

    @Query("SELECT * " +
            "FROM ${CashbackCategoryEntity.TABLE_NAME} " +
            "ORDER BY ${CashbackCategoryEntity.COLUMN_NAME} ASC")
    fun getAllCategories(): Flow<List<CashbackCategoryEntity>>

    @Query("SELECT COUNT(*) FROM ${CashbackCategoryEntity.TABLE_NAME}")
    suspend fun getCategoriesCount(): Int

    @Transaction
    @Query("SELECT * " +
            "FROM ${CashbackCategoryEntity.TABLE_NAME} " +
            "ORDER BY ${CashbackCategoryEntity.COLUMN_NAME} ASC")
    fun getCashbackCategoriesWithBankCards(): Flow<List<CashbackCategoryWithBankCardsView>>
}