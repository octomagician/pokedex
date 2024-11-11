package com.example.retrofit1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import com.example.retrofit1.api.apiPokemon;
import com.example.retrofit1.responses.pokemonResponse;
import com.example.retrofit1.models.Pokemon;
import com.example.retrofit1.adapter.PokemonAdapter;
import com.example.retrofit1.network.RetrofitClient;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ---------------- RETROFIT
        // Guardar la referencia de la instancia Retrofit en una variable
        Retrofit retrofit = RetrofitClient.getClient("https://pokeapi.co/api/v2/");

        // Instancia de la interfaz de la API
        apiPokemon currentApiPokemon = retrofit.create(apiPokemon.class);

        // ---------------- Llamada para obtener Pokémon
        int limit = 150;
        int offset = 0; // desde cuál empieza
        Call<pokemonResponse> response = currentApiPokemon.getResults(limit, offset);

        // Ejecutando la llamada asincrónica
        response.enqueue(new Callback<pokemonResponse>() {
            @Override
            public void onResponse(Call<pokemonResponse> call, Response<pokemonResponse> response) {
                if (response.isSuccessful()) {
                    List<Pokemon> pokemones = response.body().getResults();
                    Log.i("Pokemon", pokemones.get(0).getName()); //nombre del primero
                } else {
                    Log.e("Pokemon", "Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<pokemonResponse> call, Throwable t) {
                Log.e("Pokemon", "Fallo en la conexión: " + t.getMessage());
            }
        });

        // ---------------- RECYCLER VIEW
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Llamada para los 150, usa los mismos de arriba aunque sea diferente isntancia
        Call<pokemonResponse> call = currentApiPokemon.getResults(limit, offset);
        call.enqueue(new Callback<pokemonResponse>() {
            @Override
            public void onResponse(Call<pokemonResponse> call, Response<pokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    pokemonAdapter = new PokemonAdapter(pokemonList);
                    recyclerView.setAdapter(pokemonAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<pokemonResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
