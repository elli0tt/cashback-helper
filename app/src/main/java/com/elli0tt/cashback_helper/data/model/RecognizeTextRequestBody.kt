package com.elli0tt.cashback_helper.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RecognizeTextRequestBody(
    val mimeType: String = "JPEG",
    val languageCodes: List<String> = listOf("ru"),
    val model: String = "page",
    val content: String
)