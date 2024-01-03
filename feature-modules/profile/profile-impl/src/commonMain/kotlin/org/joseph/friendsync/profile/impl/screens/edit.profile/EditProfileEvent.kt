package org.joseph.friendsync.profile.impl.screens.edit.profile

sealed class EditProfileEvent {

    data class OnSaveClick(val id: Int) : EditProfileEvent()

    data object OnEditAvatar : EditProfileEvent()

    data class OnNameChanged(val value: String) : EditProfileEvent()

    data class OnLastNameChanged(val value: String) : EditProfileEvent()

    data class OnEmailChanged(val value: String) : EditProfileEvent()

    data class OnEducationChanged(val value: String) : EditProfileEvent()

    data class OnAboutMeChanged(val value: String) : EditProfileEvent()


}