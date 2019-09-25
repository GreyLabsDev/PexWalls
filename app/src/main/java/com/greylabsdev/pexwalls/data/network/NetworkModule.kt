package com.greylabsdev.pexwalls.data.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

private const val PEXELS_EDNPOINT = "https://api.pexels.com/v1/"
private const val KEY_AUTHORIZATION = "Authorization"
private const val AUTHORIZATION_TOKEN = "some_token"

val networkModule = module {

    single { createApiService<PexelsApi>(get(), PEXELS_EDNPOINT) }

    factory { createLoggingInterceptor() }

    factory { createNetworkInterceptor() }

    factory { createOkHttpClient(get(), get()) }
}

fun createLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        Timber.d(message)
    })
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

fun createNetworkInterceptor(): Interceptor {
    return Interceptor {
        val request = it.request().newBuilder()
            .addHeader(KEY_AUTHORIZATION, AUTHORIZATION_TOKEN)
            .build()
        it.proceed(request)
    }
}

fun createOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    networkInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(networkInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .hostnameVerifier { _, _ -> true }
        .retryOnConnectionFailure(false)
        .build()
}

inline fun <reified T> createApiService(okHttpClient: OkHttpClient, apiUrl: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}