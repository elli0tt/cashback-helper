//package com.elli0tt.cashback_helper.data.database.view
//
//import androidx.room.Embedded
//import androidx.room.Junction
//import androidx.room.Relation
//import com.elli0tt.cashback_helper.data.database.entity.BankCardCashbackCategoryXRefEntity
//import com.elli0tt.cashback_helper.data.database.entity.BankCardEntity
//import com.elli0tt.cashback_helper.data.database.entity.CashbackCategoryEntity
//
//data class CashbackCategoryWithBankCardsView(
//    @Embedded val cashbackCategoryEntity: CashbackCategoryEntity,
//    @Relation(
//        parentColumn = CashbackCategoryEntity.COLUMN_NAME,
//        entityColumn = BankCardEntity.COLUMN_NAME,
//        associateBy = Junction(BankCardCashbackCategoryXRefEntity::class)
//    )
//    val bankCardsEntities: List<BankCardEntity>
//)