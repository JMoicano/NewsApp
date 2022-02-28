package dev.jmoicano.newsapp.dependencyinjection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jmoicano.newsapp.BuildConfig
import dev.jmoicano.newsapp.articles.data.ArticlesDataSource
import dev.jmoicano.newsapp.articles.data.remote.ArticlesAPI
import dev.jmoicano.newsapp.articles.data.remote.ArticlesRemoteDataSource
import dev.jmoicano.newsapp.data.ErrorParser
import dev.jmoicano.newsapp.data.InstantDeserializer
import dev.jmoicano.newsapp.sources.data.SourcesDataSource
import dev.jmoicano.newsapp.sources.data.remote.SourcesAPI
import dev.jmoicano.newsapp.sources.data.remote.SourcesRemoteDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .addHeader("X-Api-Key", BuildConfig.NEWS_API_KEY)
                        .build()
                return@addInterceptor chain.proceed(request)
            }

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }

        return clientBuilder.build()
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Instant::class.java, InstantDeserializer())
            .create()
    }

    @Provides
    fun providesSourcesAPI(retrofit: Retrofit): SourcesAPI {
        return retrofit.create(SourcesAPI::class.java)
    }

    @Provides
    fun providesSourcesDataSource(
        sourcesAPI: SourcesAPI,
        errorParser: ErrorParser
    ): SourcesDataSource {
        return SourcesRemoteDataSource(sourcesAPI, errorParser)
    }

    @Provides
    fun providesArticlesAPI(retrofit: Retrofit): ArticlesAPI {
        return retrofit.create(ArticlesAPI::class.java)
    }

    @Provides
    fun providesArticlesDataSource(
        articlesAPI: ArticlesAPI,
        errorParser: ErrorParser
    ): ArticlesDataSource {
        return ArticlesRemoteDataSource(articlesAPI, errorParser)
    }

}