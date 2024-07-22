package com.himawan.weighbridge.data.repository

import com.himawan.weighbridge.data.SortBy
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.mock.MockTicketRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.junit.Test
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TicketRepositoryTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            single<TicketDataSource> { MockTicketRepository() }
        })
    }

    private lateinit var ticketRepository: TicketDataSource

    @Before
    fun setup() {
        ticketRepository = getKoin().get()
    }

    @Test
    fun `test getAllTickets`() = runBlocking {
        val result = ticketRepository.getAllTickets(SortBy.ASC)
        Assert.assertEquals(ResponseState.Success(MockTicketRepository.TICKETS), result)
    }

    @Test
    fun `test getTicket`() = runBlocking {
        val result = ticketRepository.getTicket("1234")
        Assert.assertEquals(ResponseState.Success(MockTicketRepository.TICKET_DETAIL), result)
    }

    @Test
    fun `test createTicket`() = runBlocking {
        val ticket = MockTicketRepository.TICKET_DETAIL
        val result = ticketRepository.createTicket(ticket)
        Assert.assertEquals(ResponseState.Success(Unit), result)
    }

    @Test
    fun `test updateTicket`() = runBlocking {
        val ticket = MockTicketRepository.TICKET_DETAIL
        val result = ticketRepository.updateTicket(ticket)
        Assert.assertEquals(ResponseState.Success(Unit), result)
    }

    @Test
    fun `test deleteTicket`() = runBlocking {
        val result = ticketRepository.deleteTicket("1234")
        Assert.assertEquals(ResponseState.Success(Unit), result)
    }

}