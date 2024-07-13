package com.example.hci_wd_25;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/";
    private static Retrofit retrofit = null;

    private RetrofitInstance() {
        // private constructor to prevent instantiation
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static DictionaryApi getDictionaryApi() {
        return getInstance().create(DictionaryApi.class);
    }
}
