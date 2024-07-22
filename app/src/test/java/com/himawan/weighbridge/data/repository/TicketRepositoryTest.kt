package com.himawan.weighbridge.data.repository

import com.himawan.weighbridge.data.SortBy
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.mock.MockTicketRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.junit.MockitoJUnitRunner
import org.koin.test.get
import org.koin.test.inject

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
        ticketRepository = koinTestRule.koin.get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test getAllTickets`() = runBlocking {
        val expected = MockTicketRepository.TICKETS
        val result = ticketRepository.getAllTickets(SortBy.ASC)
        Assert.assertEquals(ResponseState.Success(expected), result)
    }

    @Test
    fun `test getTicket`() = runBlocking {
        val expected = MockTicketRepository.TICKET_DETAIL
        val result = ticketRepository.getTicket("1234")
        Assert.assertEquals(ResponseState.Success(expected), result)
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

    @Test
    fun `test searchTickets`() = runBlocking {
        val expected = MockTicketRepository.TICKETS
        val result = ticketRepository.searchTickets("Suyatna")
        Assert.assertEquals(ResponseState.Success(expected), result)
    }

}