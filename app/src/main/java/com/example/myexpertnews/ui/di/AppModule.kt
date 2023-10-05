package com.example.myexpertnews.ui.di

import com.example.core.domain.usecase.AgentsInteractor
import com.example.core.domain.usecase.AgentsUseCase
import com.example.myexpertnews.ui.agents.AgentsViewModel
import com.example.myexpertnews.ui.agents.DetailAgentViewModel
import com.example.myexpertnews.ui.agents.FavoriteAgentsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AgentsUseCase> {
        AgentsInteractor(get())
    }
}

val viewModelModule = module {
    viewModel { AgentsViewModel(get()) }
    viewModel { DetailAgentViewModel(get()) }
    viewModel { FavoriteAgentsViewModel(get()) }
}

