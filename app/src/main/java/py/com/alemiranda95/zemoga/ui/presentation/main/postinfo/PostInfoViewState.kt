package py.com.alemiranda95.zemoga.ui.presentation.main.postinfo

import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.utils.PostAction

data class PostInfoViewState(
    val post: PostInfo? = null,
    val postAction: PostAction? = null,
    val postUpdated: Boolean = false,
    val errorMessage: String? = null,
    val infoMessage: String? = null,
    val showLoading: Boolean = false,
    val commentListLoaded: Boolean = false
)