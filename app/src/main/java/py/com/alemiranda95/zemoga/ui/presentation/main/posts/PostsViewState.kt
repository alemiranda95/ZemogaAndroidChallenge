package py.com.alemiranda95.zemoga.ui.presentation.main.posts

import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo

data class PostsViewState (
    val postsList: MutableList<PostInfo> = mutableListOf(),
    val errorMessage: String? = null,
    val infoMessage: String? = null,
    val showTryAgain: Boolean = false,
    val postsListLoaded: Boolean = false,
    val stopRefreshing: Boolean = true
)