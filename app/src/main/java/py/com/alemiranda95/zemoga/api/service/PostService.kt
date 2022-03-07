package py.com.alemiranda95.zemoga.api.service

import android.content.res.Resources
import py.com.alemiranda95.zemoga.domain.model.UserDto
import py.com.alemiranda95.zemoga.domain.model.CommentDto
import py.com.alemiranda95.zemoga.domain.model.PostDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class PostService(
    private val retrofit: Retrofit,
    resources: Resources
) : SafeApiRequest(resources) {

    private val postApi: PostApi by lazy {
        retrofit.create(PostApi::class.java)
    }

    private interface PostApi {
        @GET("posts")
        suspend fun getPosts(): Response<List<PostDto>>

        @GET("users/{user}")
        suspend fun getUser(
            @Path("user") user: Int
        ): Response<UserDto>

        @GET("posts/{postId}/comments")
        suspend fun getComments(
            @Path("postId") postId: Int
        ): Response<List<CommentDto>>
    }

    suspend fun getPosts(): List<PostDto> {
        return apiRequest { postApi.getPosts() }
    }

    suspend fun getUser(user: Int): UserDto {
        return apiRequest { postApi.getUser(user) }
    }

    suspend fun getComments(postId: Int): List<CommentDto> {
        return apiRequest { postApi.getComments(postId) }
    }

}