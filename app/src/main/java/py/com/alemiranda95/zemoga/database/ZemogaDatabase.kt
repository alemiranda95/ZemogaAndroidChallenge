package py.com.alemiranda95.zemoga.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import py.com.alemiranda95.zemoga.database.dao.PostInfoDao
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.database.converter.Converter

@Database(
    entities = [PostInfo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ZemogaDatabase : RoomDatabase() {
    abstract fun postDao(): PostInfoDao
}