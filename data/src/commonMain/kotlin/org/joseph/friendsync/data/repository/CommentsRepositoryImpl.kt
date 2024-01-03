package org.joseph.friendsync.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.map
import org.joseph.friendsync.data.local.dao.comments.CommentsDao
import org.joseph.friendsync.data.local.dao.post.PostDao
import org.joseph.friendsync.data.local.dao.post.RecommendedPostDao
import org.joseph.friendsync.data.mappers.CommentCloudToCommentDomainMapper
import org.joseph.friendsync.data.mappers.CommentsLocalToCommentDomainMapper
import org.joseph.friendsync.data.models.comments.CommentCloud
import org.joseph.friendsync.data.models.comments.CommentListResponse
import org.joseph.friendsync.data.models.comments.CommentResponse
import org.joseph.friendsync.data.service.CommentsService
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.repository.CommentsRepository

internal class CommentsRepositoryImpl(
    private val postDao: PostDao,
    private val recommendedPostDao: RecommendedPostDao,
    private val commentsDao: CommentsDao,
    private val commentsService: CommentsService,
    private val commentCloudToCommentDomainMapper: CommentCloudToCommentDomainMapper,
    private val commentsLocalToCommentDomainMapper: CommentsLocalToCommentDomainMapper,
    private val dispatcherProvider: DispatcherProvider,
) : CommentsRepository {

    override fun observeAllPostComments(postId: Int): Flow<List<CommentDomain>> {
        return commentsDao.fetchAllPostCommentsReactive(postId).map { comments ->
            comments.map(commentsLocalToCommentDomainMapper::map)
        }
    }

    override suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): Result<CommentDomain?> = withContext(dispatcherProvider.io) {
        callSafe {
            val response = commentsService.addCommentToPost(userId, postId, commentText)

            if (response.isSuccess()) {
                response.data?.data?.let { post -> commentsDao.insertOrUpdateComment(post) }
                postDao.incrementDecrementCommentsCount(postId, true)
                recommendedPostDao.incrementDecrementCommentsCount(postId, true)
            }
            response.map(::mapCommentResponseToDomain)
        }

    }

    override suspend fun deleteCommentById(
        postId: Int,
        commentId: Int
    ): Result<Int> = withContext(dispatcherProvider.io) {
        callSafe {
            val response = commentsService.deleteCommentById(commentId)
            if (response.isSuccess()) {
                commentsDao.deleteCommentById(commentId)
                postDao.incrementDecrementCommentsCount(postId, false)
                recommendedPostDao.incrementDecrementCommentsCount(postId, false)
            }
            response
        }

    }

    override suspend fun editCommentById(
        commentId: Int,
        editedText: String
    ): Result<Int> = withContext(dispatcherProvider.io) {
        callSafe {
            val response = commentsService.editCommentById(commentId, editedText)
            if (response.isSuccess()) {
                commentsDao.editCommentById(commentId, editedText)
            }
            response
        }

    }

    override suspend fun fetchAllPostComments(
        postId: Int
    ): Result<List<CommentDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            when (val response = commentsService.fetchAllPostComments(postId)) {
                is Result.Success -> {
                    val commentsCloud = response.data?.data ?: emptyList()
                    commentsDao.insertOrUpdateComments(commentsCloud)
                    Result.Success(commentsCloud.map(commentCloudToCommentDomainMapper::map))
                }

                is Result.Error -> {
                    if (response.message != null) Result.Error(response.message!!)
                    else Result.defaultError()
                }
            }
        }
    }

    private fun mapCommentResponseToDomain(response: CommentResponse): CommentDomain? {
        return response.data?.let(commentCloudToCommentDomainMapper::map)
    }
}