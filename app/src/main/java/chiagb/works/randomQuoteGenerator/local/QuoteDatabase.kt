package chiagb.works.randomQuoteGenerator.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import chiagb.works.randomQuoteGenerator.model.DataConverter
import chiagb.works.randomQuoteGenerator.model.QuoteModel

@Database(entities = [QuoteModel::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class QuoteDatabase: RoomDatabase()
{
    abstract fun getDAO(): QuoteModelDAO
}