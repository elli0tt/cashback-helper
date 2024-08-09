package com.elli0tt.cashback_helper.data.repo

import android.util.Log
import com.elli0tt.cashback_helper.data.database.dao.CashbackCategoriesDao
import com.elli0tt.cashback_helper.data.mapper.toCashbackCategoriesList
import com.elli0tt.cashback_helper.data.mapper.toCashbackCategoryEntitiesList
import com.elli0tt.cashback_helper.data.mapper.toCashbackCategoryEntity
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.CashbackCategoriesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class CashbackCategoriesRepoImpl(
    private val cashbackCategoriesDao: CashbackCategoriesDao
) : CashbackCategoriesRepo {
    override suspend fun addCategory(cashbackCategory: CashbackCategory) {
        Log.d(TAG, "addCategory(): $cashbackCategory")
        cashbackCategoriesDao.insertCategory(cashbackCategory.toCashbackCategoryEntity())
    }

    override suspend fun addCategories(cashbackCategories: List<CashbackCategory>) {
        Log.d(TAG, "addCategories(): ${cashbackCategories.joinToString()}")
        cashbackCategoriesDao.insertCategories(cashbackCategories.toCashbackCategoryEntitiesList())
    }

    override fun getAllCategories(): Flow<List<CashbackCategory>> {
        return cashbackCategoriesDao.getAllCategories().map { cashbackCategoryEntities ->
            Log.d(TAG, "getAllCategories(): size: ${cashbackCategoryEntities.size}")
            cashbackCategoryEntities.toCashbackCategoriesList()
        }
    }

    companion object {
        private const val TAG = "CashbackCategoriesRepoImpl"
    }
}