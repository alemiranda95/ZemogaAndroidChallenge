package py.com.alemiranda95.zemoga.ui.presentation.main.posts

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
import py.com.alemiranda95.zemoga.utils.exception.NetworkException
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postInfoRepository: PostInfoRepository,
    private val resources: Resources
): ViewModel(), IBasePostsViewModel<PostsViewState> {

    private val mutableViewState = MutableLiveData(PostsViewState())
    val viewState: LiveData<PostsViewState> get() = mutableViewState

    override fun loadPostsFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableViewState.postValue(
                postInfoRepository.getPostsFromDB().run {
                    PostsViewState(
                        postsList = toMutableList(),
                        postsListLoaded = true
                    )
                }
            )
        }
    }

    override fun errorViewState(error: String): PostsViewState {
        return viewState.value!!.copy(
            errorMessage = error,
            infoMessage = null,
            showTryAgain = false,
            stopRefreshing = true,
            postsListLoaded = false
        )
    }

    override fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            postInfoRepository.deleteAAll()
            mutableViewState.postValue(
                PostsViewState(
                    infoMessage = resources.getString(R.string.posts_deleted_message),
                    postsListLoaded = true
                )
            )
        }
    }

    override fun deletePost(postInfo: PostInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value!!.postsList.remove(postInfo)
            postInfoRepository.delete(postInfo)
            mutableViewState.postValue(
                viewState.value!!.copy(
                    postsListLoaded = true,
                    errorMessage = null,
                    infoMessage = resources.getString(R.string.post_deleted_message)
                )
            )
        }
    }

    override fun updatePost(postInfo: PostInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postInfoRepository.update(postInfo)
                mutableViewState.postValue(
                    viewState.value!!.copy(
                        errorMessage = null,
                        showTryAgain = false,
                        stopRefreshing = true,
                        postsListLoaded = true
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
                postsListLoaded = false,
                errorMessage = null,
                infoMessage = null
            )
    }

    fun loadPostsFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts = postInfoRepository.getPostsFromApi()
                postInfoRepository.insertAll(posts)
                mutableViewState.postValue(
                    PostsViewState(
                        postsList = posts.toMutableList(),
                        postsListLoaded = true
                    )
                )
            } catch (e: NetworkException) {
                mutableViewState.postValue(
                    when (e) {
                        is NetworkException.NoInternetException -> {
                            errorViewState(e.message!!)
                        }
                        is NetworkException.ApiError -> {
                            errorViewState(e.message!!).copy(
                                showTryAgain = true,
                            )
                        }
                    }
                )
            } catch (e: DatabaseException) {
                mutableViewState.postValue(
                    errorViewState(e.message!!)
                )
            }
        }
    }

}