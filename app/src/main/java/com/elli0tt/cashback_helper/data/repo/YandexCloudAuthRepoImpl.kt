package com.elli0tt.cashback_helper.data.repo

import com.elli0tt.cashback_helper.BuildConfig
import com.elli0tt.cashback_helper.data.model.YandexPassportToken
import com.elli0tt.cashback_helper.data.remote.YandexCloudAuthApi
import com.elli0tt.cashback_helper.domain.repo.YandexCloudAuthRepo
import org.koin.core.annotation.Single

@Single
class YandexCloudAuthRepoImpl(
    private val yandexCloudAuthApi: YandexCloudAuthApi
) : YandexCloudAuthRepo {
    override suspend fun getToken(): String {
        val yandexCloudTokenResponse = yandexCloudAuthApi.getToken(
            YandexPassportToken(BuildConfig.YANDEX_PASSPORT_TOKEN)
        )
        return "Bearer ${yandexCloudTokenResponse.body()?.token}"
    }
}
