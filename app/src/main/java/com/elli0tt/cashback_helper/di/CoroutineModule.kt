package com.elli0tt.cashback_helper.di

import com.elli0tt.cashback_helper.core.coroutines.dispatchers.DispatchersProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
class CoroutineModule {

    @Single
    @Named(APP_SCOPE_NAME)
    fun appScope(dispatchersProvider: DispatchersProvider) = CoroutineScope(
        SupervisorJob() + dispatchersProvider.mainImmediate
    )

    companion object {
        const val APP_SCOPE_NAME = "app_scope_name"
    }
}