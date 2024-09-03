package com.elli0tt.cashback_helper.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryXRefEntity
import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BankCardsCashbackCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCategory(cashbackCategoryEntity: CashbackCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCategories(cashbackCategoryEntities: List<CashbackCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCard(bankCardEntity: BankCardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCards(bankCardsEntities: List<BankCardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCardCategoryXRef(
        bankCardCashbackCategoryXRef: BankCardCashbackCategoryXRefEntity
    )
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCardsCategoriesXRefs(
        bankCardsCashbackCategoriesXRefs: List<BankCardCashbackCategoryXRefEntity>
    )

//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCategoriesForCard(
//        bankCardWithCashbackCategories: BankCardWithCashbackCategoriesView
//    ) {
//        insertCategories(bankCardWithCashbackCategories.cashbackCategoriesEntities)
//        bankCardWithCashbackCategories.cashbackCategoriesEntities.forEach { cashbackCategory ->
//            insertCardCategoryXRef(
//                BankCardCashbackCategoryXRefEntity(
//                    bankCardName = bankCardWithCashbackCategories.bankCardEntity.name,
//                    cashbackCategoryName = cashbackCategory.name,
//                    isSelected = false,
//                    percent = -100f
//                )
//            )
//        }
//    }

    @Update
    abstract suspend fun updateCardCategoryXRef(
        bankCardCashbackCategoryXRefEntity: BankCardCashbackCategoryXRefEntity
    )

    @Query(
        "SELECT * " +
                "FROM ${CashbackCategoryEntity.TABLE_NAME} " +
                "ORDER BY ${CashbackCategoryEntity.COLUMN_NAME} ASC"
    )
    abstract fun getAllCategories(): Flow<List<CashbackCategoryEntity>>

    @Query(
        "SELECT * " +
                "FROM ${BankCardEntity.TABLE_NAME} " +
                "ORDER BY ${BankCardEntity.COLUMN_ORDER} ASC"
    )
    abstract fun getAllCards(): Flow<List<BankCardEntity>>

//    @Transaction
//    @Query(
//        "SELECT * " +
//                "FROM ${CashbackCategoryEntity.TABLE_NAME} " +
//                "ORDER BY ${CashbackCategoryEntity.COLUMN_NAME} ASC"
//    )
//    abstract fun getCategoriesWithCards(): Flow<List<CashbackCategoryWithBankCardsView>>

//    @Transaction
//    @Query(
//        "SELECT * " +
//                "FROM ${BankCardEntity.TABLE_NAME} " +
//                "ORDER BY ${BankCardEntity.COLUMN_NAME} ASC"
//    )
//    abstract fun getBankCardsWithCashbackCategories(): Flow<List<BankCardWithCashbackCategoriesView>>

//    @Query(
//        "SELECT ${BankCardEntity.TABLE_NAME}.${BankCardEntity.COLUMN_NAME} AS bankCardName, " +
//                "${CashbackCategoryEntity.TABLE_NAME}.${CashbackCategoryEntity.COLUMN_NAME} AS cashbackCategoryName, " +
//                "${BankCardCashbackCategoryXRefEntity.COLUMN_PERCENT} AS percent " +
//                "FROM ${BankCardEntity.TABLE_NAME} " +
//                "CROSS JOIN ${BankCardCashbackCategoryXRefEntity.TABLE_NAME} " +
//                "ON ${BankCardEntity.TABLE_NAME}.${BankCardEntity.COLUMN_NAME} = ${BankCardCashbackCategoryXRefEntity.TABLE_NAME}.${BankCardEntity.COLUMN_NAME} " +
//                "CROSS JOIN ${CashbackCategoryEntity.TABLE_NAME} " +
//                "ON ${BankCardCashbackCategoryXRefEntity.TABLE_NAME}.${CashbackCategoryEntity.COLUMN_NAME} = ${CashbackCategoryEntity.TABLE_NAME}.${CashbackCategoryEntity.COLUMN_NAME}"
//    )
////    abstract fun getCardsWithCategories(): Flow<List<CashbackCategoryForBankCard>>
//    abstract suspend fun getCardsWithCategories(): List<BankCardWithCashbackCategoriesViewNew>

    data class BankCardCashbackCategoryPercent(
        val bankCardName: String,
        val cashbackCategoryName: String?,
        val percent: Float?
    )

    @Query("SELECT * FROM ${BankCardCashbackCategoryXRefEntity.TABLE_NAME}")
    abstract fun getAllCardsCategoriesXRefs():
            Flow<List<BankCardCashbackCategoryXRefEntity>>

    @Query("SELECT COUNT(*) FROM ${CashbackCategoryEntity.TABLE_NAME}")
    abstract suspend fun getCategoriesCount(): Int

    @Query("SELECT COUNT(*) FROM ${BankCardEntity.TABLE_NAME}")
    abstract suspend fun getCardsCount(): Int
}

//(SELECT ${CashbackCategoryEntity.TABLE_NAME}.${CashbackCategoryEntity.COLUMN_NAME} AS name, " +
//"${BankCardCashbackCategoryXRefEntity.TABLE_NAME}.${BankCardCashbackCategoryXRefEntity.COLUMN_PERCENT} AS percent " +
//"FROM ${CashbackCategoryEntity.TABLE_NAME} " +
//"INNER JOIN ${BankCardCashbackCategoryXRefEntity.TABLE_NAME} " +
//"ON ${CashbackCategoryEntity.TABLE_NAME}.${CashbackCategoryEntity.COLUMN_NAME} = ${BankCardCashbackCategoryXRefEntity.TABLE_NAME}.${CashbackCategoryEntity.COLUMN_NAME}) " +
//"AS cashbackCategories