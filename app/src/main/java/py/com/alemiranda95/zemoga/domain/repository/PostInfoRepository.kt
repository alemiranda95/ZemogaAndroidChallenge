package py.com.alemiranda95.zemoga.domain.repository

import py.com.alemiranda95.zemoga.api.service.PostService
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.database.dao.PostInfoDao
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfoMapper
import javax.inject.Inject

class PostInfoRepository @Inject constructor(
    private val postDao: PostInfoDao,
    private val postService: PostService
) {

    //Dao
    fun getPostsFromDB() = postDao.getPosts()

    fun getFavoritedPosts() = postDao.getFavoritedPosts()

    fun deleteAAll() = postDao.deleteAAll()

    fun delete(postInfo: PostInfo) = postDao.delete(postInfo)

    fun insert(postInfo: PostInfo) = postDao.insert(postInfo)

    fun insertAll(postInfoData: List<PostInfo>) = postDao.insertAll(postInfoData)

    fun update(postInfo: PostInfo) = postDao.update(postInfo)

    //Service
    suspend fun getPostsFromApi() = PostInfoMapper.mapPostsDto(postService.getPosts())

    suspend fun getUser(user: Int) = postService.getUser(user)

    suspend fun getComments(postId: Int) = postService.getComments(postId)

}