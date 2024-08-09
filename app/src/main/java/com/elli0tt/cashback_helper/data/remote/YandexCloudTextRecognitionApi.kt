package com.elli0tt.cashback_helper.data.remote

import com.elli0tt.cashback_helper.data.model.RecognizeTextRequestBody
import com.elli0tt.cashback_helper.data.model.RecognizeTextResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface YandexCloudTextRecognitionApi {
    @Headers(
        "Content-Type: application/json",
        "x-folder-id: b1g4kur238cu0ujoeqm7",
        "x-data-logging-enabled: true"
    )
    @POST("/ocr/v1/recognizeText")
    suspend fun recognizeText(
        @Header("Authorization") token: String,
        @Body body: RecognizeTextRequestBody
    ): Response<RecognizeTextResponse>

    companion object {
        const val BASE_URL = "https://ocr.api.cloud.yandex.net"
    }
}