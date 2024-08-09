package com.elli0tt.cashback_helper.di

import android.content.ContentResolver
import android.content.Context
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AndroidModule {
    @Single
    fun contentResolver(context: Context): ContentResolver = context.contentResolver
}