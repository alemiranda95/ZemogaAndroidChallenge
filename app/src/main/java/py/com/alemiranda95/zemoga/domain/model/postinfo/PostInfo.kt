package py.com.alemiranda95.zemoga.domain.model.postinfo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import py.com.alemiranda95.zemoga.domain.model.CommentDto
import py.com.alemiranda95.zemoga.domain.model.UserDto

@Entity(tableName = "post")
@Parcelize
data class PostInfo(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var read: Boolean = false,
    var favorite: Boolean = false,
    var user: @RawValue UserDto = UserDto(),
    var commentList: @RawValue List<CommentDto> = emptyList()
) : Parcelable
