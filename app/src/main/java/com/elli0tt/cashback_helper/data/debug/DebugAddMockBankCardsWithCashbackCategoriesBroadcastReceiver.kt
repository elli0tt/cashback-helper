package com.elli0tt.cashback_helper.data.debug

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.elli0tt.cashback_helper.BuildConfig
import com.elli0tt.cashback_helper.di.CoroutineModule
import com.elli0tt.cashback_helper.domain.use_case.DebugAddMockBankCardsWithCashbackCategoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class DebugAddMockBankCardsWithCashbackCategoriesBroadcastReceiver :
    BroadcastReceiver(), KoinComponent {

    private val debugAddMockBankCardsWithCashbackCategoriesUseCase: DebugAddMockBankCardsWithCashbackCategoriesUseCase by inject()

    private val appScope: CoroutineScope by inject(named(CoroutineModule.APP_SCOPE_NAME))

    override fun onReceive(context: Context, intent: Intent) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onReceive(): DebugAddMockBankCardsWithCashbackCategoriesBroadcastReceiver")
            appScope.launch {
                debugAddMockBankCardsWithCashbackCategoriesUseCase()
            }
        }
    }

    companion object {
        private const val TAG = "DebugAddMockBankCardsWithCashbackCategoriesBroadcastReceiver"
    }
}