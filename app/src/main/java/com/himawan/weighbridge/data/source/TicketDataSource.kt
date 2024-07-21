package com.himawan.weighbridge.data.source

import androidx.lifecycle.MutableLiveData
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.domain.model.Ticket
import kotlinx.coroutines.flow.Flow

interface TicketDataSource {

    suspend fun getAllTickets(): ResponseState<List<Ticket?>>
    suspend fun getTicket(id: String): ResponseState<Ticket?>
    suspend fun createTicket(ticket: Ticket): ResponseState<Unit>
    suspend fun updateTicket(ticket: Ticket): ResponseState<Unit>
    suspend fun deleteTicket(id: String): ResponseState<Unit>

}