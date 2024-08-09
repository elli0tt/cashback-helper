package com.elli0tt.cashback_helper.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YandexPassportToken(val yandexPassportOauthToken: String)
