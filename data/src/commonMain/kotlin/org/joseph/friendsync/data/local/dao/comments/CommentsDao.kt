package org.joseph.friendsync.data.local.dao.comments

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.models.comments.CommentCloud

@Dao
interface CommentsDao {

    @Query("SELECT * FROM comment_table WHERE post_id = :postId")
    fun fetchAllPostCommentsReactive(postId: Int): Flow<List<CommentEntity>>

    @Query("SELECT * FROM comment_table WHERE post_id = :postId")
    suspend fun fetchAllPostComments(postId: Int): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateComment(comment: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateComments(comments: List<CommentEntity>)

    @Delete
    suspend fun deleteComment(commentEntity: CommentEntity)

    @Query("DELETE FROM comment_table WHERE id = :commentId")
    suspend fun deleteCommentById(commentId: Int)

    @Query("UPDATE comment_table SET comment = :editedText WHERE id = :commentId")
    suspend fun editCommentById(commentId: Int, editedText: String)
}