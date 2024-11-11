package com.example.retrofit1.network;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/") // endpoint
                    .addConverterFactory(GsonConverterFactory.create()) // Conversión de JSON a objetos
                    .build();
        }
        return retrofit;
    }
}




