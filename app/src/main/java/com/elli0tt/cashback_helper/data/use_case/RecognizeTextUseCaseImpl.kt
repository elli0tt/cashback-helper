package com.elli0tt.cashback_helper.data.use_case

import android.content.ContentResolver
import android.util.Base64
import android.util.Log
import androidx.core.net.toUri
import com.elli0tt.cashback_helper.core.coroutines.dispatchers.DispatchersProvider
import com.elli0tt.cashback_helper.data.model.RecognizeTextRequestBody
import com.elli0tt.cashback_helper.data.model.RecognizeTextResponse
import com.elli0tt.cashback_helper.data.remote.YandexCloudTextRecognitionApi
import com.elli0tt.cashback_helper.domain.model.RecognizedText
import com.elli0tt.cashback_helper.domain.repo.YandexCloudAuthRepo
import com.elli0tt.cashback_helper.domain.use_case.RecognizeTextUseCase
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import org.koin.core.annotation.Single
import retrofit2.Response

@Single
class RecognizeTextUseCaseImpl(
    private val contentResolver: ContentResolver,
    private val yandexCloudTextRecognitionApi: YandexCloudTextRecognitionApi,
    private val yandexCloudAuthRepo: YandexCloudAuthRepo,
    private val dispatchersProvider: DispatchersProvider
) : RecognizeTextUseCase {

    override suspend fun invoke(imageUri: String): RecognizedText =
        withContext(dispatchersProvider.io) {
            val recognizedText = loadImageBase64(imageUri)?.let { imageBase64 ->
                yandexCloudTextRecognitionApi.recognizeText(
                    token = yandexCloudAuthRepo.getToken(),
                    RecognizeTextRequestBody(content = imageBase64)
                ).toRecognizedText()
            } ?: RecognizedText.Failure

            Log.d(
                TAG,
                "recognizeTextUseCase(): imageUri: $imageUri, recognizedText: $recognizedText"
            )
            recognizedText
        }

    private fun loadImageBase64(imageUri: String): String? {
        return contentResolver.openInputStream(imageUri.toUri())?.use { inputStream ->
            val byteArray = inputStream.source().buffer().readByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }

    private fun Response<RecognizeTextResponse>.toRecognizedText(): RecognizedText {
        return if (this.isSuccessful) {
            this.body()?.fullText?.let { json ->
                RecognizedText.Success(json)
            } ?: RecognizedText.Failure
        } else {
            RecognizedText.Failure
        }
    }

    companion object {
        private const val TAG = "RecognizeTextUseCaseImpl"
    }
}
