import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.ui.list.PokemonListViewModel

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onShowDetail: (Int) -> Unit
) {
    val pokemons by viewModel.pokemons.collectAsState()

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(8.dp, top=60.dp)
        ) {
            items(
                items = pokemons,
                key = { it.id }
            ) { pokemon ->
                Card(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { onShowDetail(pokemon.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)

                ) {
                    Row {
                        Image(
                            painter = painterResource(pokemon.spriteId),
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Crop,
                            contentDescription = pokemon.name
                        )
                        Text(
                            text = pokemon.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}


