package py.com.alemiranda95.zemoga.ui.presentation.main.favorites

import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo

data class FavoritesViewState(
    val favoritesList: MutableList<PostInfo> = mutableListOf(),
    val errorMessage: String? = null,
    val infoMessage: String? = null,
    val favoritesListLoaded: Boolean = false,
)
