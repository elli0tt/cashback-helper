package com.elli0tt.cashback_helper.data.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.elli0tt.cashback_helper.BuildConfig
import com.elli0tt.cashback_helper.data.ext.getValue
import com.elli0tt.cashback_helper.data.model.YandexPassportToken
import com.elli0tt.cashback_helper.data.remote.YandexCloudAuthApi
import com.elli0tt.cashback_helper.domain.repo.YandexCloudAuthRepo
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.hours

@Single
class YandexCloudAuthRepoImpl(
    private val yandexCloudAuthApi: YandexCloudAuthApi,
    private val dataStore: DataStore<Preferences>
) : YandexCloudAuthRepo {

    override suspend fun getToken(): String? {
        if (canUseTokenFromCache()) {
            Log.d(TAG, "getToken(): getTokenFromCache()")
            return getTokenFromCache()!!
        }

        val yandexCloudTokenResponse = yandexCloudAuthApi.getToken(
            YandexPassportToken(BuildConfig.YANDEX_PASSPORT_TOKEN)
        )

        if (yandexCloudTokenResponse.isSuccessful && yandexCloudTokenResponse.body() != null) {
            val token = "Bearer ${yandexCloudTokenResponse.body()?.token}"
            saveToken(token)
            return token
        }
        return null
    }

    private suspend fun canUseTokenFromCache(): Boolean {
        val lastUpdateTime = getTokenLastUpdateTimeFromCache()
        return lastUpdateTime != null &&
                (lastUpdateTime - System.currentTimeMillis()) < TOKEN_UPDATE_PERIOD
    }

    private suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[YANDEX_CLOUD_TOKEN_KEY] = token
            preferences[YANDEX_CLOUD_TOKEN_LAST_UPDATE_TIME_KEY] = System.currentTimeMillis()
        }
    }

    private suspend fun getTokenFromCache(): String? =
        dataStore.getValue(YANDEX_CLOUD_TOKEN_KEY)

    private suspend fun getTokenLastUpdateTimeFromCache(): Long? =
        dataStore.getValue(YANDEX_CLOUD_TOKEN_LAST_UPDATE_TIME_KEY)

    companion object {
        private const val TAG = "YandexCloudAuthRepoImpl"

        private val YANDEX_CLOUD_TOKEN_KEY = stringPreferencesKey("yandex_cloud_token")
        private val YANDEX_CLOUD_TOKEN_LAST_UPDATE_TIME_KEY =
            longPreferencesKey("yandex_cloud_token_last_update_time")

        private val TOKEN_UPDATE_PERIOD: Long = 1.hours.inWholeMilliseconds
    }
}
