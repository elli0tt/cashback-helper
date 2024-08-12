package com.elli0tt.cashback_helper.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CashbackCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(cashbackCategoryEntity: CashbackCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(cashbackCategoryEntities: List<CashbackCategoryEntity>)

    @Query("SELECT * FROM ${CashbackCategoryEntity.TABLE_NAME}")
    fun getAllCategories(): Flow<List<CashbackCategoryEntity>>
}