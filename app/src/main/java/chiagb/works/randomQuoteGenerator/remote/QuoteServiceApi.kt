package chiagb.works.randomQuoteGenerator.remote

import chiagb.works.randomQuoteGenerator.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteServiceApi {
    @GET("random")
    suspend fun getRandomQuote(): Response<QuoteModel?>?
}