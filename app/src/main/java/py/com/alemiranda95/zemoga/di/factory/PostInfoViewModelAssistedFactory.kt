package py.com.alemiranda95.zemoga.di.factory

import dagger.assisted.AssistedFactory
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.ui.presentation.main.postinfo.PostInfoViewModel

@AssistedFactory
interface PostInfoViewModelAssistedFactory {
    fun create(postInfo: PostInfo): PostInfoViewModel
}