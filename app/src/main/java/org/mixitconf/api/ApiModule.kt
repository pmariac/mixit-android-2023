package org.mixitconf.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.mixitconf.BuildConfig
import org.mixitconf.Properties
import org.mixitconf.Properties.DEFAULT_TIMEOUT_SECONDS
import org.mixitconf.api.adapter.InstantAtUtcJsonAdapter
import org.mixitconf.api.interceptor.NetworkConnectionInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit


val apiModule = module {

    single<UserApiRepository> {
        val retrofit = get<Retrofit> {
            parametersOf(Properties.MIXIT_USER_API, get<OkHttpClient>{
                parametersOf(get<List<Interceptor>>(named("interceptors")), null)
            })
        }
        retrofit.create()
    }

    single<TalkApiRepository> {
        val retrofit = get<Retrofit> {
            parametersOf(Properties.MIXIT_EVENT_API, get<OkHttpClient>{
                parametersOf(get<List<Interceptor>>(named("interceptors")), null)
            })
        }
        retrofit.create()
    }

    single(named("interceptors")) {
        listOf(NetworkConnectionInterceptor())
    }

    /**
     * Declare a factory instance of [Retrofit].
     */
    factory<Retrofit> { (baseUrl: String, okHttpClient: OkHttpClient) ->
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    /**
     * Declare a single instance of [OkHttpClient].
     */
    factory { (interceptors: List<Interceptor>?) ->
        OkHttpClient.Builder().apply {
            connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(get<HttpLoggingInterceptor>())
            }
            interceptors?.forEach { addInterceptor(it) }
        }.build()
    }

    /**
     * Declare a single instance of [Moshi].
     */
    single {
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(Instant::class.java, InstantAtUtcJsonAdapter())
            .build()
    }

    /**
     * Declare a single instance of [HttpLoggingInterceptor].
     */
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
