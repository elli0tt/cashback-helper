package com.elli0tt.cashback_helper.data

import com.elli0tt.cashback_helper.data.model.RecognizeTextResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class RecognizeTextResponseAdapter : JsonAdapter<RecognizeTextResponse>() {

    @FromJson
    override fun fromJson(jsonReader: JsonReader): RecognizeTextResponse {
        var fullText = ""
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            val nextName = jsonReader.nextName()
            if (nextName == RESULT_NAME) {
                fullText = findTextAnnotationInResult(jsonReader)
            } else {
                jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
        return RecognizeTextResponse(fullText)
    }

    private fun findTextAnnotationInResult(jsonReader: JsonReader): String {
        var fullText = ""
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            val nextName = jsonReader.nextName()
            if (nextName == TEXT_ANNOTATION_NAME) {
                fullText = findFullTextInTextAnnotation(jsonReader)
            } else {
                jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
        return fullText
    }

    private fun findFullTextInTextAnnotation(jsonReader: JsonReader): String {
        var fullText = ""
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            val nextName = jsonReader.nextName()
            if (nextName == FULL_TEXT_NAME) {
                fullText = jsonReader.nextString()
            } else {
                jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
        return fullText
    }

    override fun toJson(
        jsonWriter: JsonWriter,
        recognizeTextResponse: RecognizeTextResponse?
    ) = Unit

    companion object {
        private const val RESULT_NAME = "result"
        private const val TEXT_ANNOTATION_NAME = "textAnnotation"
        private const val FULL_TEXT_NAME = "fullText"
    }
}