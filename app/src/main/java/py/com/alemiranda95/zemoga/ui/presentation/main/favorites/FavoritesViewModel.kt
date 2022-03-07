package py.com.alemiranda95.zemoga.ui.presentation.main.favorites

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.domain.repository.PostInfoRepository
import py.com.alemiranda95.zemoga.ui.interfaces.IBasePostsViewModel
import py.com.alemiranda95.zemoga.utils.exception.DatabaseException
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val postInfoRepository: PostInfoRepository,
    private val resources: Resources
): ViewModel(), IBasePostsViewModel<FavoritesViewState> {

    private val mutableViewState = MutableLiveData(FavoritesViewState())
    val viewState: LiveData<FavoritesViewState> get() = mutableViewState

    override fun loadPostsFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableViewState.postValue(
                postInfoRepository.getFavoritedPosts().run {
                    FavoritesViewState(
                        favoritesList = toMutableList(),
                        favoritesListLoaded = true
                    )
                }
            )
        }
    }

    override fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                viewState.value!!.favoritesList.forEach() {
                    it.favorite = false
                    postInfoRepository.update(it)
                }
                mutableViewState.postValue(
                    FavoritesViewState(
                        infoMessage = resources.getString(R.string.favorites_deleted_message),
                        favoritesListLoaded = true
                    )
                )
            } catch (e: DatabaseException) {
                mutableViewState.postValue(
                    errorViewState(e.message!!)
                )
            }
        }
    }

    override fun onDestroy() {
        mutableViewState.value =
            viewState.value!!.copy(
                favoritesListLoaded = false,
                errorMessage = null,
                infoMessage = null
            )
    }

    override fun errorViewState(error: String): FavoritesViewState {
        return viewState.value!!.copy(
            errorMessage = error,
            favoritesListLoaded = false
        )
    }

    override fun deletePost(postInfo: PostInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value!!.favoritesList.remove(postInfo)
            postInfoRepository.delete(postInfo)
            mutableViewState.postValue(
                viewState.value!!.copy(
                    favoritesListLoaded = true,
                    errorMessage = null,
                    infoMessage = resources.getString(R.string.post_deleted_message)
                )
            )
        }
    }

    override fun updatePost(postInfo: PostInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(!postInfo.favorite)
                    viewState.value!!.favoritesList.remove(postInfo)
                postInfoRepository.update(postInfo)
                mutableViewState.postValue(
                    viewState.value!!.copy(
                        favoritesListLoaded = true,
                        errorMessage = null,
                        infoMessage = null
                    )
                )
            } catch (e: DatabaseException) {
                mutableViewState.postValue(
                    errorViewState(e.message!!)
                )
            }
        }
    }

}