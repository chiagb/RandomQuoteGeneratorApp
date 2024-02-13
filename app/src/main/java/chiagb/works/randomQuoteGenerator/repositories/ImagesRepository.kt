package chiagb.works.randomQuoteGenerator.repositories

import chiagb.works.randomQuoteGenerator.ApiKeyProvider
import chiagb.works.randomQuoteGenerator.BuildConfig
import chiagb.works.randomQuoteGenerator.UnsplashApiKey
import chiagb.works.randomQuoteGenerator.remote.ImageServiceApi
import javax.inject.Inject

class  ImagesRepository @Inject constructor(
    private val imageServiceApi: ImageServiceApi,
    private val apiKey : UnsplashApiKey
) {

    suspend fun getRemoteImage(clientId :String = apiKey.key) =
        imageServiceApi.getRandomImage(clientId)

}