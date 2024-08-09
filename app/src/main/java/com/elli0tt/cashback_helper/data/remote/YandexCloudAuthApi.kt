package com.elli0tt.cashback_helper.data.remote

import com.elli0tt.cashback_helper.data.model.YandexCloudAuthToken
import com.elli0tt.cashback_helper.data.model.YandexPassportToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface YandexCloudAuthApi {

    @POST("/iam/v1/tokens")
    suspend fun getToken(@Body passportToken: YandexPassportToken): Response<YandexCloudAuthToken>

    companion object {
        const val BASE_URL = "https://iam.api.cloud.yandex.net"
    }
}