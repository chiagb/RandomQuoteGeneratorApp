package chiagb.works.randomQuoteGenerator.viewModels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import chiagb.works.randomQuoteGenerator.model.QuoteModel
import chiagb.works.randomQuoteGenerator.model.UnsplashPhoto
import chiagb.works.randomQuoteGenerator.repositories.ImagesRepository
import chiagb.works.randomQuoteGenerator.repositories.QuoteRepository
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
    private val imagesRepository: ImagesRepository,
    private val imageRequest: ImageRequest.Builder,
    private val imageLoader: ImageLoader,
) : ViewModel() {

    private val _savedQuotes = MutableStateFlow<List<QuoteModel>>(emptyList())
    val quotes = _savedQuotes.asStateFlow()

    private val _quote: MutableStateFlow<QuoteModel?> = MutableStateFlow(null)
    val quote = _quote.asStateFlow()

    private val _image: MutableStateFlow<UnsplashPhoto?> = MutableStateFlow(null)
    val image = _image.asStateFlow()

    fun fetchSavedQuotes() {
        viewModelScope.launch {
            quoteRepository.getAllQuotes().flowOn(Dispatchers.IO)
                .collect { quotes: List<QuoteModel> ->
                    _savedQuotes.emit(quotes)
                }
        }
    }

    fun fetchQuoteFromRemote() {
        viewModelScope.launch {
            val response = quoteRepository.getRemoteQuote()
            if (response != null && response.isSuccessful)
                _quote.value = response.body()
        }
    }

    fun fetchImageFromRemote() {
        viewModelScope.launch {
            val response = imagesRepository.getRemoteImage()
            if (response != null && response.isSuccessful)
                _image.value = response.body()
        }
    }

    fun addQuote(quote: QuoteModel) {
        quoteRepository.addQuote(quote)
        fetchSavedQuotes()
    }

    fun deleteAllQuotes() {
        quoteRepository.deleteAllQuotes()
        fetchSavedQuotes()
    }

    val color = _image.asStateFlow()
        .map {
            it?.let{
                val request = imageRequest
                    .data(it.urls.small)
                    .allowHardware(false) // Disable hardware bitmaps.
                    .build()


                val result = (imageLoader.execute(request) as SuccessResult).drawable
                val bitmap = (result as BitmapDrawable).bitmap

                val color = Palette.Builder(bitmap).generate().dominantSwatch?.rgb
                if (color != null) {
                    Color(ColorUtils.blendARGB(color, Color.Black.toArgb(), 0.2f))
                } else Color.DarkGray
            }
        }
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Color.DarkGray)


}