package org.joseph.friendsync.auth.impl.di

import org.joseph.friendsync.auth.impl.enter.LoginWithEmailViewModel
import org.joseph.friendsync.auth.impl.login.LoginViewModel
import org.joseph.friendsync.auth.impl.sign.SignUpViewModel
import org.joseph.friendsync.domain.validations.EmailValidation
import org.joseph.friendsync.domain.validations.NameValidation
import org.joseph.friendsync.domain.validations.PasswordValidation
import org.koin.dsl.module

val authScreenModule = module {
    factory { LoginWithEmailViewModel(get(), get()) }
    factory { params ->
        LoginViewModel(
            email = params.get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { params ->
        SignUpViewModel(email = params.get(), get(), get(), get(), get(), get(), get(), get(), get())
    }

    factory<EmailValidation> { EmailValidation.Default() }
    factory<PasswordValidation> { PasswordValidation.Default() }
    factory<NameValidation> { NameValidation.Default() }
}
