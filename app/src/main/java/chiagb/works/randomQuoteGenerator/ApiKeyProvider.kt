package chiagb.works.randomQuoteGenerator

import android.content.Context
import java.io.IOException
import java.util.Properties

object ApiKeyProvider {
    private const val PROPERTIES_FILE_NAME = "api_keys.properties"

    fun getUnsplashApiKey(context: Context): UnsplashApiKey {
        val properties = Properties()
        try {
            val inputStream = context.assets.open(PROPERTIES_FILE_NAME)
            properties.load(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val api_key = properties.getProperty("UNSPLASH_API_KEY")
        return UnsplashApiKey(api_key)
    }
}

data class UnsplashApiKey(
    val key : String
)