package com.elli0tt.cashback_helper.domain.repo

interface YandexCloudAuthRepo {
    suspend fun getToken(): String?
}