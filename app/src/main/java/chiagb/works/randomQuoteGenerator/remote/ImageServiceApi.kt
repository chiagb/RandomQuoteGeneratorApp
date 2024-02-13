package chiagb.works.randomQuoteGenerator.remote

import chiagb.works.randomQuoteGenerator.BuildConfig
import chiagb.works.randomQuoteGenerator.model.UnsplashPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageServiceApi {
    @GET("/photos/random")
    suspend fun getRandomImage(@Query ("client_id") clientId: String): Response<UnsplashPhoto?>?
}