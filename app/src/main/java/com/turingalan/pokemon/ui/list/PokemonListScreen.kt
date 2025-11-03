import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.ui.detail.DetailUiState
import com.turingalan.pokemon.ui.detail.PokemonDetailScreen
import com.turingalan.pokemon.ui.detail.PokemonDetailViewModel
import com.turingalan.pokemon.ui.list.ListlUiState
import com.turingalan.pokemon.ui.list.PokemonListViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun PokemonList(
    onShowDetail: (Int) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    when(uiState){

        is ListlUiState.New -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            viewModel.getAllPokemons()
        }
        is ListlUiState.Loaded -> {
            val pokemon = (uiState as ListlUiState.Loaded).pokemons

            PokemonListScreen(
                pokemons = pokemon,
                modifier = Modifier,
                onShowDetail= onShowDetail

            )
        }
        is ListlUiState.Error -> {
            val errorMessage = (uiState as ListlUiState.Error).message
            PokemonListScreen(
                error = errorMessage,
                modifier = Modifier,
                )
        }
    }
}

@Composable
fun PokemonListScreen(
    pokemons : List<Pokemon>?=null,
    modifier: Modifier = Modifier,
    onShowDetail: (Int) -> Unit?= {},
    error:String?=null
) {
if(pokemons!=null){
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
}else{
    Text( modifier = Modifier
        .padding(top = 90.dp),
        text = error!!)
}

}


