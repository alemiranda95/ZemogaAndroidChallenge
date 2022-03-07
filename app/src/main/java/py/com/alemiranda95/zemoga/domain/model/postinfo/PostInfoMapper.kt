package py.com.alemiranda95.zemoga.domain.model.postinfo

import py.com.alemiranda95.zemoga.domain.model.PostDto

object PostInfoMapper {
    fun mapPostsDto(postsDto: List<PostDto>): List<PostInfo> {
        return postsDto.map {
            PostInfo(
                id = it.id,
                userId = it.userId,
                title = it.title,
                body = it.body
            )
        }
    }
}