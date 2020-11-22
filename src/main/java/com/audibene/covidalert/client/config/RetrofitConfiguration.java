package com.audibene.covidalert.client.config;

import com.audibene.covidalert.client.CoronaApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfiguration {

    @Bean
    public CoronaApi coronaApi(
            OkHttpClient okHttpClient,
            ObjectMapper objectMapper,
            @Value("${corona.api.base.url}") String baseUrl
    ) {
        final var retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build();

        return retrofit.create(CoronaApi.class);
    }
}
