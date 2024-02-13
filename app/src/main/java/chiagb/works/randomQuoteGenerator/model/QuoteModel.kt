package chiagb.works.randomQuoteGenerator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

@Entity(tableName = "quotes")
data class QuoteModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String? = null,
    val author: String? = null,

    @TypeConverters(DataConverter::class)
    val tags: List<String>? = null,

    val authorSlug: String? = null,
    val length: Int? = null,
    val dateAdded: String? = null,
    val dateModified: String? = null
) : Serializable

class DataConverter {

    @TypeConverter
    fun fromListToString(list: List<*>): String {
        val type = object: TypeToken<List<*>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toData(dataString: String?): List<String> {
        if(dataString == null || dataString.isEmpty()) {
            return mutableListOf()
        }
        val type: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(dataString, type)
    }
}