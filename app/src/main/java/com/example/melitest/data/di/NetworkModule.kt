package com.example.melitest.data.di

import com.example.melitest.data.remote.api.MercadoLibreApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import com.example.melitest.BuildConfig
import com.example.melitest.domain.util.Constants.BASE_URL


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val req = chain.request()
        val requiresAuth = req.header("Requires-Auth") == "true"
        val b = req.newBuilder()
            .header("Accept", "application/json")
            .header("User-Agent", "MeliTest/1.0 (Android)")
            .removeHeader("Requires-Auth")
        if (requiresAuth) {
            val token = BuildConfig.MELI_ACCESS_TOKEN
            if (token.isNotBlank()) b.header("Authorization", "Bearer $token")
        } else {
            b.removeHeader("Authorization")
        }
        chain.proceed(b.build())
    }



    @Provides @Singleton
    fun provideOkHttp(auth: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        return OkHttpClient.Builder()
            .addInterceptor(auth)
            .addInterceptor(logging)
            .build()
    }
    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()

    @Provides @Singleton
    fun provideMeliApi(retrofit: Retrofit): MercadoLibreApi =
        retrofit.create(MercadoLibreApi::class.java)
}
