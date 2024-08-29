package com.elli0tt.cashback_helper.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryXRefEntity
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.view.BankCardWithCashbackCategoriesView
import kotlinx.coroutines.flow.Flow

@Dao
interface BankCardsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBankCard(bankCardEntity: BankCardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBankCards(bankCardsEntities: List<BankCardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBankCardCashbackCategoryXRef(
        bankCardCashbackCategoryXRefEntity: BankCardCashbackCategoryXRefEntity
    )

    @Query(
        "SELECT * " +
                "FROM ${BankCardEntity.TABLE_NAME} " +
                "ORDER BY ${BankCardEntity.COLUMN_ORDER} ASC"
    )
    fun getAllBankCards(): Flow<List<BankCardEntity>>

    @Transaction
    @Query(
        "SELECT * " +
                "FROM ${BankCardEntity.TABLE_NAME} " +
                "ORDER BY ${BankCardEntity.COLUMN_NAME} ASC"
    )
    fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategoriesView>>

    @Update
    suspend fun updateBankCardCashbackCategoryXRef(
        bankCardCashbackCategoryXRefEntity: BankCardCashbackCategoryXRefEntity
    )

    @Query("SELECT * FROM ${BankCardCashbackCategoryXRefEntity.TABLE_NAME}")
    fun getAllBankCardsCashbackCategoriesXRefs():
            Flow<List<BankCardCashbackCategoryXRefEntity>>

    @Query("SELECT COUNT(*) FROM ${BankCardEntity.TABLE_NAME}")
    suspend fun getBankCardsCount(): Int
}