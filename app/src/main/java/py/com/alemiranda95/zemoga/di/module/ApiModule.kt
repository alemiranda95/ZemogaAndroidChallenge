package py.com.alemiranda95.zemoga.di.module

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import py.com.alemiranda95.zemoga.api.network.NetworkBuilder
import py.com.alemiranda95.zemoga.api.service.PostService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideRetrofit(application: Application): Retrofit {
        return NetworkBuilder.build(application.applicationContext)
    }

    @Provides
    fun providePostervice(retrofit: Retrofit, resources: Resources): PostService =
        PostService(retrofit, resources)

}