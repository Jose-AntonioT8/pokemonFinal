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
                PokemonListItemCard(
                    modifier = Modifier.padding(8.dp),
                    pokemonId = pokemon.id,
                    name = pokemon.name,
                    spriteId = pokemon.spriteId,
                    onShowDetail = onShowDetail
                )
            }
        }
    }
}

@Composable
fun PokemonListItemCard(
    modifier: Modifier = Modifier,
    pokemonId: Int,
    name: String,
    spriteId: Int,
    onShowDetail: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onShowDetail(pokemonId) }
    ) {
        Row {
            Image(
                painter = painterResource(spriteId),
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop,
                contentDescription = name
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,

            )
        }
    }
}
