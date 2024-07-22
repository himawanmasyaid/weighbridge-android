package com.himawan.weighbridge.mock

import com.himawan.weighbridge.data.SortBy
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.domain.model.Ticket

class MockTicketRepository : TicketDataSource {

    override suspend fun getAllTickets(sort: SortBy): ResponseState<List<Ticket?>> {
        return ResponseState.Success(TICKETS)
    }

    override suspend fun getTicket(id: String): ResponseState<Ticket?> {
        return ResponseState.Success(TICKET_DETAIL)
    }

    override suspend fun createTicket(ticket: Ticket): ResponseState<Unit> {
        return ResponseState.Success(Unit)
    }

    override suspend fun updateTicket(ticket: Ticket): ResponseState<Unit> {
        return ResponseState.Success(Unit)
    }

    override suspend fun deleteTicket(id: String): ResponseState<Unit> {
        return ResponseState.Success(Unit)
    }

    override suspend fun searchTickets(query: String, sort: SortBy): ResponseState<List<Ticket?>> {
        return ResponseState.Success(TICKETS)
    }

    companion object {

        val TICKET_DETAIL = Ticket(
            id = "1234",
            date = "2023-03-15",
            time = "14:30:00",
            licenseNumber = "BG 1234 AB",
            driverName = "Arsad Sapardie",
            inboundWeight = 10000,
            outboundWeight = 8000,
            netWeight = 2000,
            createdAt = 1678903400,
            updatedAt = 1678903400
        )

        val TICKETS = listOf(
            Ticket(
                id = "1234",
                date = "2024-07-22",
                time = "14:30:00",
                licenseNumber = "BG 1234 AB",
                driverName = "Suyatna",
                inboundWeight = 10000,
                outboundWeight = 5000,
                netWeight = 5000,
                createdAt = 1690732200000,
                updatedAt = 1690732200000
            ),
            Ticket(
                id = "1235",
                date = "2024-07-21",
                time = "09:00:00",
                licenseNumber = "BG 1111 KK",
                driverName = "Himawan",
                inboundWeight = 5000,
                outboundWeight = 1000,
                netWeight = 4000,
                createdAt = 1690028400000,
                updatedAt = 1690028400000
            )
        )
    }

}