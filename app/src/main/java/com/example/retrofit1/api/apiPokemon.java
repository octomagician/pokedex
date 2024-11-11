package com.example.retrofit1.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.retrofit1.responses.pokemonResponse;

public interface apiPokemon {
    @GET("pokemon")
    Call<pokemonResponse> getResults(
        @Query("limit") int limit,  // número
        @Query("offset") int offset // paginación
    );

}
