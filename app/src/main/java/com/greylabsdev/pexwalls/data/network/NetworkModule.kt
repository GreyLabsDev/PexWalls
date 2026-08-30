package com.greylabsdev.pexwalls.data.network

import com.greylabsdev.pexwalls.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val PEXELS_EDNPOINT = "https://api.pexels.com/"
private const val KEY_AUTHORIZATION = "Authorization"

val networkModule = module {

    single { createApiService<PexelsApi>(get(), PEXELS_EDNPOINT) }

    factory { createLoggingInterceptor() }

    factory { createNetworkInterceptor() }

    factory { createOkHttpClient(get(), get()) }
}

fun createLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor { message -> Timber.d(message) }.apply {
        redactHeader(KEY_AUTHORIZATION)
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}

fun createNetworkInterceptor(): Interceptor {
    return Interceptor {
        val request = it.request().newBuilder()
            .addHeader(KEY_AUTHORIZATION, BuildConfig.PEXELS_API_KEY)
            .build()
        it.proceed(request)
    }
}

fun createOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    networkInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(networkInterceptor)
        .retryOnConnectionFailure(false)
        .build()
}

inline fun <reified T> createApiService(okHttpClient: OkHttpClient, apiUrl: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}
