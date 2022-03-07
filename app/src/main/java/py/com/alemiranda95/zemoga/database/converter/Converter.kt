package py.com.alemiranda95.zemoga.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import py.com.alemiranda95.zemoga.domain.model.CommentDto
import py.com.alemiranda95.zemoga.domain.model.UserDto
import java.lang.reflect.Type

object Converter {

    @TypeConverter
    fun fromStringToUser(value: String): UserDto {
        return Gson().fromJson(value, UserDto::class.java)
    }

    @TypeConverter
    fun fromUserToString(user: UserDto): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun fromStringToComment(value: String): List<CommentDto> {
        val type: Type = object : TypeToken<List<CommentDto>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCommentToString(value: List<CommentDto>): String {
        return Gson().toJson(value)
    }
}