package org.joseph.friendsync.di


import org.joseph.friendsync.auth.login.LoginViewModel
import org.joseph.friendsync.auth.sign.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


fun appModules() = listOf(appModule, managersModule, viewModelsModule)

private val appModule = module {

}
private val viewModelsModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }

}


private val managersModule = module {}