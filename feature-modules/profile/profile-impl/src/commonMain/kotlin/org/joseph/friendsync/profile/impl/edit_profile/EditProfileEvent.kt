package org.joseph.friendsync.profile.impl.edit_profile

sealed class EditProfileEvent {

    data object OnSaveClick : EditProfileEvent()

    data object OnEditAvatar : EditProfileEvent()

    data class OnNameChanged(val value: String) : EditProfileEvent()

    data class OnLastNameChanged(val value: String) : EditProfileEvent()

    data class OnEmailChanged(val value: String) : EditProfileEvent()

    data class OnEducationChanged(val value: String) : EditProfileEvent()

    data class OnAboutMeChanged(val value: String) : EditProfileEvent()


}