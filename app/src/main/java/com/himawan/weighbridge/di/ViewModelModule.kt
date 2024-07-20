package com.himawan.weighbridge.di

import com.himawan.weighbridge.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel(get()) }

}