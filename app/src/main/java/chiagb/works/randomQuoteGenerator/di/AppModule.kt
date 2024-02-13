package chiagb.works.randomQuoteGenerator.di

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import chiagb.works.randomQuoteGenerator.ApiKeyProvider
import chiagb.works.randomQuoteGenerator.UnsplashApiKey
import chiagb.works.randomQuoteGenerator.local.QuoteDatabase
import chiagb.works.randomQuoteGenerator.local.QuoteModelDAO
import chiagb.works.randomQuoteGenerator.model.QuoteModel
import chiagb.works.randomQuoteGenerator.remote.ImageServiceApi
import chiagb.works.randomQuoteGenerator.remote.QuoteServiceApi
import chiagb.works.randomQuoteGenerator.repositories.ImagesRepository
import chiagb.works.randomQuoteGenerator.repositories.QuoteRepository
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideQuoteApi() : QuoteServiceApi = Retrofit.Builder()
        .baseUrl("https://api.quotable.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuoteServiceApi::class.java)

    @Provides
    @Singleton
    fun provideImageApi() : ImageServiceApi {
        val logging = HttpLoggingInterceptor()

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ImageServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRequestBuilder(@ApplicationContext context: Context): ImageRequest.Builder {
        val request = ImageRequest.Builder(context)
            return request
    }
    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context) = ImageLoader(context)


    @Provides
    @Singleton
    fun provideApiKey(@ApplicationContext context: Context) : UnsplashApiKey = ApiKeyProvider.getUnsplashApiKey(context)


    @Provides
    fun provideQuoteDao(appDatabase: QuoteDatabase): QuoteModelDAO {
        return appDatabase.getDAO()
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(serviceApi: QuoteServiceApi, quoteModelDAO: QuoteModelDAO) = QuoteRepository(quoteModelDAO,serviceApi)

    @Provides
    @Singleton
    fun provideImageRepository(imageServiceApi: ImageServiceApi, unsplashApiKey: UnsplashApiKey) = ImagesRepository(imageServiceApi, unsplashApiKey)

    @Provides
    @Singleton
    fun createDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, QuoteDatabase::class.java, "quotedb")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideEntity() = QuoteModel()
}