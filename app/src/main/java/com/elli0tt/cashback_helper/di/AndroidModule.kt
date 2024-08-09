package com.elli0tt.cashback_helper.di

import android.content.ContentResolver
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AndroidModule {
    @Single
    fun contentResolver(context: Context): ContentResolver = context.contentResolver

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

    @Single
    fun dataStore(context: Context): DataStore<Preferences> = context.dataStore
}