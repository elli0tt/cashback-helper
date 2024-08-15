package com.elli0tt.cashback_helper.ui.screen.cashback.table

import androidx.lifecycle.ViewModel
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import com.elli0tt.cashback_helper.domain.repo.CashbackCategoriesRepo
import com.elli0tt.cashback_helper.domain.utils.CashbackPercentFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CashbackCategoriesTableViewModel(
    bankCardsRepo: BankCardsRepo,
    cashbackCategoriesRepo: CashbackCategoriesRepo
): ViewModel() {

    val bankCardsNamesList: Flow<List<String>> = bankCardsRepo.getAllBankCards().onlyNames()

    private val cashbackCategoriesList: Flow<List<CashbackCategory>> =
        cashbackCategoriesRepo.getAllCategories()
    val cashbackCategoriesNames: Flow<List<String>> = cashbackCategoriesList.onlyNames()

    val cashbackCategoriesTable: Flow<List<List<String>>> =
        bankCardsRepo.getBankCardsWithCashbackCategories()
            .combine(cashbackCategoriesList) { bankCardsWithCashbackCategories, allCashbackCategories ->

                val resultTable = mutableListOf<List<String>>()

                repeat(bankCardsWithCashbackCategories.size) { bankCardIndex ->
                    resultTable += List(size = allCashbackCategories.size) { cashbackCategoryIndex ->
                        val cashbackCategory = allCashbackCategories[cashbackCategoryIndex]
                        if (bankCardsWithCashbackCategories[bankCardIndex].cashbackCategories.map { it.name }
                                .contains(cashbackCategory.name)) {
                            CashbackPercentFormatter.format(cashbackCategory.percent)
                        } else {
                            ""
                        }
                    }
                }
                resultTable
            }

    @JvmName("bankCardsNames")
    private fun Flow<List<BankCard>>.onlyNames(): Flow<List<String>> = this.map { bankCards ->
        bankCards.map { bankCard -> bankCard.name }
    }

    @JvmName("cashbackCategoriesNames")
    private fun Flow<List<CashbackCategory>>.onlyNames(): Flow<List<String>> =
        this.map { cashbackCategories ->
            cashbackCategories.map { cashbackCategory -> cashbackCategory.name }
        }
}