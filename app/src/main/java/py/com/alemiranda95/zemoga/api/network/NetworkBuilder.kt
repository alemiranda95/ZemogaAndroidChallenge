package py.com.alemiranda95.zemoga.api.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkBuilder {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun build(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(initializeRestClient(context))
            .build();
    }

    private fun initializeRestClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(initializeCach(context))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // logging interceptor
            .connectTimeout(180, TimeUnit.SECONDS) // connect timeout
            .readTimeout(180, TimeUnit.SECONDS)
            .build() // socket timeout
    }

    private fun initializeCach(context: Context): Cache {
        val file = File(context.cacheDir, "cache");
        return Cache(file, 10 * 1024 * 1024)
    }
}