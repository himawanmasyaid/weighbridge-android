package com.himawan.weighbridge.di

import com.himawan.weighbridge.view.main.MainViewModel
import com.himawan.weighbridge.view.weighing_create.WeighingCreateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { WeighingCreateViewModel(get()) }

}