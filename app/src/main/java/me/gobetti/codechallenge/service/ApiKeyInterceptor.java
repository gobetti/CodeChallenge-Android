package me.gobetti.codechallenge.service;

import java.io.IOException;

import me.gobetti.codechallenge.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

final class ApiKeyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        HttpUrl newUrl = originalRequest.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.ApiKey)
                .build();

        Request newRequest = originalRequest.newBuilder().url(newUrl).build();
        return chain.proceed(newRequest);
    }
}
