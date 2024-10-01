package com.example.jollycat.di

import com.example.jollycat.data.local.helpers.CartHelper
import com.example.jollycat.data.local.helpers.CatsHelper
import com.example.jollycat.data.local.helpers.UserHelper
import com.example.jollycat.data.repo.AuthRepository
import com.example.jollycat.data.repo.MainRepository
import com.example.jollycat.ui.catdetail.CatDetailViewModel
import com.example.jollycat.ui.login.LoginViewModel
import com.example.jollycat.ui.main.cart.CartViewModel
import com.example.jollycat.ui.main.home.HomeViewModel
import com.example.jollycat.ui.main.profile.ProfileViewModel
import com.example.jollycat.ui.register.RegisterViewModel
import com.example.jollycat.utils.SettingsPreferences
import com.example.jollycat.utils.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataStoreModules = module {
    single { SettingsPreferences(androidContext().dataStore) }
}

val databaseModules = module {
    single { UserHelper.getInstance(androidContext()) }
    single { CatsHelper.getInstance(androidContext()) }
    single { CartHelper.getInstance(androidContext()) }
}

val repositoryModules = module {
    factory { AuthRepository(get(), get()) }
    factory { MainRepository(androidContext(), get(), get()) }
}

val viewModelModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { CartViewModel(get(), get()) }
    viewModel { CatDetailViewModel(get(), get()) }
}