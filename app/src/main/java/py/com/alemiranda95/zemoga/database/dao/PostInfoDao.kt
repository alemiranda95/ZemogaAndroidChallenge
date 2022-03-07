package py.com.alemiranda95.zemoga.database.dao

import androidx.room.*
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.utils.exception.DatabaseException

@Dao
interface PostInfoDao {

    @Query("select * from post")
    fun getPosts(): List<PostInfo>

    @Query("select * from post where id = :id")
    fun getPost(id: Int): PostInfo

    @Query("select * from post where favorite = 1")
    fun getFavoritedPosts(): List<PostInfo>

    @Query("delete from post")
    fun deleteAAll()

    @Delete
    fun delete(postInfo: PostInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(DatabaseException::class)
    fun insert(postInfo: PostInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(DatabaseException::class)
    fun insertAll(postInfoData: List<PostInfo>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Throws(DatabaseException::class)
    fun update(postInfo: PostInfo)

}