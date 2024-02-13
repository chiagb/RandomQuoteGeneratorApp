package chiagb.works.randomQuoteGenerator

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import chiagb.works.randomQuoteGenerator.model.QuoteModel
import chiagb.works.randomQuoteGenerator.viewModels.HomeViewModel
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val viewModel = hiltViewModel<HomeViewModel>()
    val savedQuotesState = viewModel.quotes.collectAsState()
    val quote = viewModel.quote.collectAsState()
    val image = viewModel.image.collectAsState()
    val color = viewModel.color.collectAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchQuoteFromRemote()
        viewModel.fetchImageFromRemote()
        viewModel.fetchSavedQuotes()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Random quote generator",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Button(
                        onClick = {
                            viewModel.fetchQuoteFromRemote()
                            viewModel.fetchImageFromRemote()
                        }
                    ) {
                        Text(text = "Get new quote")
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(15.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor =
                            color.value
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(25.dp)) {
                            image.value?.let { it1 ->
                                AsyncImage(
                                    model = it1.urls.small,
                                    contentDescription = it1.altDescription,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp, 200.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                            quote.value?.content?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 24.sp,
                                )
                            }
                            quote.value?.author?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
                item {

                    Spacer(modifier = Modifier.height(15.dp))
                }
                item {
                    Row {
                        Button(
                            onClick = {
                                quote.value?.let { it1 ->
                                    viewModel.addQuote(
                                        quote = it1
                                    )
                                }
                                Log.d("Main Activity", quote.value.toString())
                            }
                        ) {
                            Text(text = "Save Quote")
                        }
                        Button(
                            onClick = {
                                quote.value?.let { it1 ->
                                    viewModel.deleteAllQuotes()
                                }

                                Log.d("Main Activity", quote.value.toString())
                            }
                        ) {
                            Text(text = "Delete Quotes")
                        }
                    }
                }

                savedQuotesState.value.let { it1 -> savedQuotes(quotes = it1) }


            }

        }
    )
}

fun LazyListScope.savedQuotes(quotes: List<QuoteModel>) =
    items(quotes) { message ->
        message.content?.let { it1 -> Text(it1) }
        Spacer(modifier = Modifier.height(10.dp))
    }
