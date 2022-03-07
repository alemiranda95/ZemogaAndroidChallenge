package py.com.alemiranda95.zemoga.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDto (
    var name: String = "",
    var email: String = "",
    var website: String = "",
    var phone: String = ""
) : Parcelable

