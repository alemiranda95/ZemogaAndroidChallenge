package py.com.alemiranda95.zemoga.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentDto(
    var body: String = "",
    val email: String = ""
) : Parcelable