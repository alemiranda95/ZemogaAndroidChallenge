package py.com.alemiranda95.zemoga.utils

import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo

sealed class PostAction (postInfo: PostInfo) {
    class Update(postInfo: PostInfo) : PostAction(postInfo)
    class Delete(postInfo: PostInfo) : PostAction(postInfo)
}
