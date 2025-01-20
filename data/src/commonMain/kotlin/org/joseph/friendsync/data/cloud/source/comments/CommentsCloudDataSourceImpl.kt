package org.joseph.friendsync.data.cloud.source.comments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.cloud.mappers.CommentCloudToDataMapper
import org.joseph.friendsync.data.cloud.models.CommentCloud
import org.joseph.friendsync.data.cloud.service.comments.CommentsService
import org.joseph.friendsync.data.models.comments.AddCommentRequestParams
import org.joseph.friendsync.data.models.comments.CommentData
import org.joseph.friendsync.domain.repository.CommentId

/**
 * Реализация облачного источника данных для управления комментариями.
 *
 * Класс предоставляет методы для добавления, получения, удаления и редактирования комментариев через облачный сервис.
 *
 * @property commentsService Сервис для взаимодействия с API комментариев.
 * @property dispatcherProvider Провайдер диспетчеров для переключения контекста выполнения корутин.
 * @property commentCloudToDataMapper Маппер из объекта [CommentCloud] в [CommentData].
 */
internal class CommentsCloudDataSourceImpl(
    private val commentsService: CommentsService,
    private val dispatcherProvider: DispatcherProvider,
    private val commentCloudToDataMapper: CommentCloudToDataMapper,
) : CommentsCloudDataSource {

    override suspend fun addCommentToPost(params: AddCommentRequestParams): CommentData {
        return withContext(dispatcherProvider.io) {
            try {
                val comment = commentsService.addCommentToPost(params).data
                commentCloudToDataMapper.map(checkNotNull(comment))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to add comment from cloud", e)
            }
        }
    }

    override suspend fun fetchPostComments(postId: Int): List<CommentData> {
        return withContext(dispatcherProvider.io) {
            try {
                val comments = commentsService.fetchAllPostComments(postId).data ?: emptyList()
                comments.map(commentCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to fetch comments from cloud", e)
            }
        }
    }

    override suspend fun deleteCommentById(commentId: Int): CommentId {
        return withContext(dispatcherProvider.io) {
            try {
                commentsService.deleteCommentById(commentId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to delete comment from cloud", e)
            }
        }
    }

    override suspend fun editCommentById(commentId: Int, editedText: String): CommentId {
        return withContext(dispatcherProvider.io) {
            try {
                commentsService.editCommentById(commentId, editedText)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to edit comment from cloud", e)
            }
        }
    }
}