package prieto.fernando.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import prieto.fernando.ApiService
import retrofit2.Retrofit

@Module
class ChuckNorrisApiModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        internal fun provideChuckNorrisApi(retrofit: Retrofit) =
            retrofit.create(ApiService::class.java)

        @Provides
        @JvmStatic
        @Singleton
        internal fun provideChuckNorrisRetrofit(
            httpBuilder: OkHttpClient.Builder,
            retrofitBuilder: Retrofit.Builder
        ) = retrofitBuilder
            .client(httpBuilder.build())
            .build()
    }
}
