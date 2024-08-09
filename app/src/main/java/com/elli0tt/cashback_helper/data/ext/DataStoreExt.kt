package com.elli0tt.cashback_helper.data.ext

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

suspend fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>): T? =
    this.data.map { it[key] }.first()