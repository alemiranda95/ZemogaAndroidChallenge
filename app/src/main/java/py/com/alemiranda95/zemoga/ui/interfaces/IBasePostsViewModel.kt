package py.com.alemiranda95.zemoga.ui.interfaces

import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo

interface IBasePostsViewModel<T> : IBaseViewModel<T> {
    fun loadPostsFromDB()
    fun deleteAll()
    fun deletePost(postInfo: PostInfo)
    fun updatePost(postInfo: PostInfo)
    fun onDestroy()
}