package com.elli0tt.cashback_helper.data.use_case

import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import com.elli0tt.cashback_helper.domain.use_case.DebugAddMockBankCardsWithCashbackCategoriesUseCase
import org.koin.core.annotation.Single
import kotlin.random.Random

@Single
class DebugAddMockBankCardsWithCashbackCategoriesUseCaseImpl(
    private val bankCardsRepo: BankCardsRepo
) : DebugAddMockBankCardsWithCashbackCategoriesUseCase {

    override suspend operator fun invoke() {
        generateMockBankCardsWithCashbackCategories().forEach {
            bankCardsRepo.addBankCardWithCashbackCategories(it)
        }
    }

    private suspend fun generateMockBankCardsWithCashbackCategories(): List<BankCardWithCashbackCategories> {
        val bankCardsCount = 10
        val resultList = mutableListOf<BankCardWithCashbackCategories>()
        val existingBankCardsCount = bankCardsRepo.getBankCardsCount()
        repeat(bankCardsCount) { index ->
            resultList += BankCardWithCashbackCategories(
                bankCard = BankCard(
                    name = "Bank Card $index",
                    order = existingBankCardsCount + index
                ),
                generateMockCashbackCategories()
            )
        }
        return resultList
    }

    private fun generateMockCashbackCategories(): List<CashbackCategory> {
        val cashbackCategoriesCount = 5
        val resultList = mutableListOf<CashbackCategory>()
        repeat(cashbackCategoriesCount) {
            resultList += CashbackCategory(
                name = "Category ${Random.nextInt(1, 10)}",
                percent = Random.nextDouble(1.0, 30.0).toInt().toFloat()
            )
        }
        return resultList
    }
}