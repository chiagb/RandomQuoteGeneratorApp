package chiagb.works.randomQuoteGenerator.repositories

import chiagb.works.randomQuoteGenerator.model.QuoteModel
import chiagb.works.randomQuoteGenerator.local.QuoteModelDAO
import chiagb.works.randomQuoteGenerator.remote.QuoteServiceApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class  QuoteRepository @Inject constructor(
    private val quoteDao: QuoteModelDAO,
    private val serviceApi: QuoteServiceApi
) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addQuote(newQuote: QuoteModel) {
        coroutineScope.launch(Dispatchers.IO) {
            quoteDao.addSavedQuote(newQuote)
        }
    }

    fun getAllQuotes(): Flow<List<QuoteModel>> {
        return quoteDao.getAllSavedQuotes()
    }

    fun updateQuoteDetails(newQuote: QuoteModel) {
        coroutineScope.launch(Dispatchers.IO) {
            quoteDao.updateSavedQuote(newQuote)
        }
    }
    fun deleteAllQuotes() {
        coroutineScope.launch(Dispatchers.IO) {
            quoteDao.deleteSavedQuotes()
        }
    }

    suspend fun getRemoteQuote() =
        serviceApi.getRandomQuote()

}