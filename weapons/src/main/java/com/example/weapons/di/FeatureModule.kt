package com.example.weapons.di

import com.example.weapons.ui.WeaponsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeaponsViewModel(get()) }
}