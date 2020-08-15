package com.game.fallingwords.di.modules

import androidx.annotation.NonNull
import com.game.fallingwords.network.APIinterface
import com.game.fallingwords.utils.Constants
import com.game.fallingwords.utils.Constants.Companion.BASEURL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val logger = HttpLoggingInterceptor()
        httpClient.addInterceptor(logger)
            .addInterceptor { chain ->
                val original = chain.request()
                val request: Request.Builder = original.newBuilder()
                request.header("Content-Type", "application/json")
                chain.proceed(request.build())
            }
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASEURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIinterface(@NonNull retrofit: Retrofit): APIinterface {
        return retrofit.create(APIinterface::class.java)
    }
}