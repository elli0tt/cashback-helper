package com.elli0tt.cashback_helper.data.ext

import androidx.room.RoomDatabase
import com.elli0tt.cashback_helper.BuildConfig

fun <T : RoomDatabase> RoomDatabase.Builder<T>.onDebugFallbackToDestructiveMigration(): RoomDatabase.Builder<T> {
    if (BuildConfig.DEBUG) {
        return this.fallbackToDestructiveMigration()
    }
    return this
}