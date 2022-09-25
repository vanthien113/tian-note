package com.vanthien113.tiannote.data.di

import android.app.Application
import com.vanthien113.tiannote.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object {
        private const val READ_TIMEOUT: Long = 30
        private const val WRITE_TIMEOUT: Long = 30
        private const val CONNECTION_TIMEOUT: Long = 30
    }

    /**
     * Provide ok http cache cache.
     *
     * @param application the application
     * @return the cache
     */
    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    /**
     * Provide ok http client ok http client.
     *
     * @param cache the cache
     * @return the ok http client
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.cache(cache)
        httpClientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val builder: Request.Builder = initializeHeader(chain)
            val request: Request = builder.build()
            chain.proceed(request)
        })
        httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            httpClientBuilder.addInterceptor(logging)
            logging.level = HttpLoggingInterceptor.Level.BODY
        }
        return httpClientBuilder.build()
    }

    /**
     * Init header
     *
     * @param chain interceptor
     * @return Request
     */
    private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
        val originRequest: Request = chain.request()
        val newUrl: String = chain.request().url.toString()
        val builder: Request.Builder = originRequest
            .newBuilder()
            .url(newUrl)
            .method(originRequest.method, originRequest.body)
        builder.addHeader("Content-Type", "application/json")
        return builder
    }

    /**
     * Provide retrofit retrofit.
     *
     * @param gson         the gson
     * @param okHttpClient the ok http client
     * @return the retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }
}
