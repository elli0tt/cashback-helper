package com.elli0tt.cashback_helper.core.coroutines.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Single

@Single
class DefaultDispatchersProvider : DispatchersProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val mainImmediate: CoroutineDispatcher
        get() = Dispatchers.Main.immediate

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
