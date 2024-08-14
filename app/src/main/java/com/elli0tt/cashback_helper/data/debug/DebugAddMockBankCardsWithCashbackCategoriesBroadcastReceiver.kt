package com.elli0tt.cashback_helper.data.debug

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.elli0tt.cashback_helper.BuildConfig
import com.elli0tt.cashback_helper.di.CoroutineModule
import com.elli0tt.cashback_helper.domain.model.BankCard
import com.elli0tt.cashback_helper.domain.model.BankCardWithCashbackCategories
import com.elli0tt.cashback_helper.domain.model.CashbackCategory
import com.elli0tt.cashback_helper.domain.repo.BankCardsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.random.Random

class DebugAddMockBankCardsWithCashbackCategoriesBroadcastReceiver :
    BroadcastReceiver(), KoinComponent {

    private val bankCardsRepo: BankCardsRepo by inject()

    private val appScope: CoroutineScope by inject(named(CoroutineModule.APP_SCOPE_NAME))

    override fun onReceive(context: Context, intent: Intent) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onReceive(): DebugAddMockBankCardsWithCashbackCategoriesBroadcastReceiver")
            appScope.launch {
                generateMockBankCardsWithCashbackCategories().forEach {
                    bankCardsRepo.addBankCardWithCashbackCategories(it)
                }
            }
        }
    }

    private fun generateMockBankCardsWithCashbackCategories(): List<BankCardWithCashbackCategories> {
        val bankCardsCount = 10
        val resultList = mutableListOf<BankCardWithCashbackCategories>()
        repeat(bankCardsCount) { index ->
            resultList += BankCardWithCashbackCategories(
                bankCard = BankCard(name = "Bank Card $index"),
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
                percent = Random.nextDouble(1.0, 30.0).toFloat()
            )
        }
        return resultList
    }

    companion object {
        private const val TAG = "DebugAddMockBankCardsWithCashbackCategoriesBroadcastReceiver"
    }
}