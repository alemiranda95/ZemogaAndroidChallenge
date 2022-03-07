package py.com.alemiranda95.zemoga.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import py.com.alemiranda95.zemoga.database.ZemogaDatabase
import py.com.alemiranda95.zemoga.database.dao.PostInfoDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(application: Application): ZemogaDatabase =
        Room.databaseBuilder(
            application,
            ZemogaDatabase::class.java, "ZemogaAndroidChallenge.db"
        ).build()

    @Provides
    fun providePostDao(database: ZemogaDatabase): PostInfoDao = database.postDao()

}