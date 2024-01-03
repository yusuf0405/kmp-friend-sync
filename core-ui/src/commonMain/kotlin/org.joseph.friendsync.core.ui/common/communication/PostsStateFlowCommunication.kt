package org.joseph.friendsync.core.ui.common.communication

import org.joseph.friendsync.ui.components.models.Post


interface PostsStateFlowCommunication : StateFlowCommunication<List<Post>> {

    class Default : StateFlowStateFlowCommunicationImpl<List<Post>>(emptyList()),
        PostsStateFlowCommunication
}