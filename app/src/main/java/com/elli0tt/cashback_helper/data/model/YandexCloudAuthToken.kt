package com.elli0tt.cashback_helper.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YandexCloudAuthToken(@Json(name = "iamToken") val token: String, val expiresAt: String)
