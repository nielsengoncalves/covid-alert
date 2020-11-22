package com.audibene.covidalert.client.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public OkHttpClient okHttpClient() {
        final var httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        final var httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(httpLoggingInterceptor);

        return httpClientBuilder.build();
    }
}
