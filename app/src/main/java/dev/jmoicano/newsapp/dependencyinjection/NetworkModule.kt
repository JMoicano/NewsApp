package dev.jmoicano.newsapp.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jmoicano.newsapp.BuildConfig
import dev.jmoicano.newsapp.data.ErrorParser
import dev.jmoicano.newsapp.sourceslist.data.SourcesListDataSource
import dev.jmoicano.newsapp.sourceslist.data.remote.SourcesListAPI
import dev.jmoicano.newsapp.sourceslist.data.remote.SourcesListRemoteDataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .addHeader("X-Api-Key", BuildConfig.NEWS_API_KEY)
                        .build()
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }

    @Provides
    fun providesSourcesListAPI(retrofit: Retrofit): SourcesListAPI {
        return retrofit.create(SourcesListAPI::class.java)
    }

    @Provides
    fun providesSourcesListDataSource(
        sourcesListAPI: SourcesListAPI,
        errorParser: ErrorParser
    ): SourcesListDataSource {
        return SourcesListRemoteDataSource(sourcesListAPI, errorParser)
    }

}