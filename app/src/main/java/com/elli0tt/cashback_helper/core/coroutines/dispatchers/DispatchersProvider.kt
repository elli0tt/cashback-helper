package com.elli0tt.cashback_helper.core.coroutines.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
    val io: CoroutineDispatcher
}
