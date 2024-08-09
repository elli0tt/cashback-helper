package com.elli0tt.cashback_helper.di

import com.elli0tt.cashback_helper.data.RecognizeTextResponseAdapter
import com.elli0tt.cashback_helper.data.remote.YandexCloudAuthApi
import com.elli0tt.cashback_helper.data.remote.YandexCloudTextRecognitionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {
    @Single
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Single
    fun moshi(): Moshi = Moshi.Builder()
        .add(RecognizeTextResponseAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Single
    fun yandexCloudApi(okHttpClient: OkHttpClient, moshi: Moshi): YandexCloudTextRecognitionApi =
        Retrofit.Builder()
            .baseUrl(YandexCloudTextRecognitionApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(YandexCloudTextRecognitionApi::class.java)

    @Single
    fun yandexCloudAuthApi(okHttpClient: OkHttpClient, moshi: Moshi): YandexCloudAuthApi =
        Retrofit.Builder()
            .baseUrl(YandexCloudAuthApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(YandexCloudAuthApi::class.java)
}
