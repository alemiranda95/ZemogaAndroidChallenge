package py.com.alemiranda95.zemoga.ui.presentation.main.postinfo

import android.content.res.Resources
import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.di.factory.PostInfoViewModelAssistedFactory
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.domain.repository.PostInfoRepository
import py.com.alemiranda95.zemoga.utils.PostAction
import py.com.alemiranda95.zemoga.utils.exception.DatabaseException
import py.com.alemiranda95.zemoga.utils.exception.NetworkException

class PostInfoViewModel @AssistedInject constructor(
    private val postInfoRepository: PostInfoRepository,
    private val resources: Resources,
    @Assisted private val postInfo: PostInfo
): ViewModel() {

    class Factory(
        private val assistedFactory: PostInfoViewModelAssistedFactory,
        private val postInfo: PostInfo,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(postInfo) as T
        }
    }

    private val mutableViewState = MutableLiveData(
        PostInfoViewState(
            post = postInfo.apply { read = true },
            commentListLoaded = true)
    )
    val viewState: LiveData<PostInfoViewState> get() = mutableViewState

    init {
        loadPostInfoFromApi()
    }

    fun loadPostInfoFromApi() {
        mutableViewState.value = viewState.value!!.copy(
            showLoading = true
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                mutableViewState.postValue(
                    viewState.value!!.apply {
                        post!!.user = postInfoRepository.getUser(post.userId)
                        post.commentList = postInfoRepository.getComments(post.id)
                    }.copy(
                        postAction = PostAction.Update(postInfo),
                        postUpdated = true,
                        commentListLoaded = true,
                        errorMessage = null,
                        infoMessage = null,
                        showLoading = false
                    )
                )
            } catch (e: NetworkException) {
                mutableViewState.postValue(
                    when (e) {
                        is NetworkException.NoInternetException -> {
                            errorViewState(e.message!!)
                        }
                        is NetworkException.ApiError -> {
                            errorViewState(e.message!!)
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

    private fun errorViewState(error: String): PostInfoViewState {
        return viewState.value!!.copy(
            errorMessage = error,
            infoMessage = null,
            showLoading = false,
            commentListLoaded = false,
            postUpdated = false
        )
    }

    fun setFavorite() {
        var isFavorite: Boolean
        mutableViewState.value =
            viewState.value!!.apply {
                isFavorite = !post!!.favorite
                post.favorite = isFavorite
            }.copy(
                postAction = PostAction.Update(postInfo),
                postUpdated = true,
                errorMessage = null,
                infoMessage = if (isFavorite) resources.getString(R.string.post_favorited)
                            else resources.getString(R.string.post_unfavorited)
            )
    }

    fun deletePost() {
        mutableViewState.value =
            onDestroyViewState().copy(
                postUpdated = true,
                postAction = PostAction.Delete(postInfo)
            )
    }

    fun onDestroy() {
        mutableViewState.value = onDestroyViewState()
    }

    private fun onDestroyViewState() : PostInfoViewState {
        return viewState.value!!.copy(
            postUpdated = false,
            commentListLoaded = false,
            errorMessage = null,
            infoMessage = null,
            showLoading = false
        )
    }

}