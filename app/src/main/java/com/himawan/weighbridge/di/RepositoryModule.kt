package com.himawan.weighbridge.di

import com.himawan.weighbridge.data.repository.TicketRepository
import com.himawan.weighbridge.data.source.TicketDataSource
import org.koin.dsl.module

val repositoryModule = module {

    single<TicketDataSource> { TicketRepository() }

}