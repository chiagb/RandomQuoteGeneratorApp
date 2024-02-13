package chiagb.works.randomQuoteGenerator.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import chiagb.works.randomQuoteGenerator.model.QuoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteModelDAO
{
    @Insert
    suspend fun addSavedQuote(quote: QuoteModel)

    @Query("SELECT * FROM quotes ORDER BY id DESC")
    fun getAllSavedQuotes(): Flow<List<QuoteModel>>

    @Update
    suspend fun updateSavedQuote(quote: QuoteModel)

    @Delete
    suspend fun deleteSavedQuote(quote: QuoteModel)

    @Query("DELETE from quotes")
    suspend fun deleteSavedQuotes()

}