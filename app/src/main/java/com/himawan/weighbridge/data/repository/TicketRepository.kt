package com.himawan.weighbridge.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.himawan.weighbridge.common.DateUtil
import com.himawan.weighbridge.data.SortBy
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.domain.model.Ticket
import com.himawan.weighbridge.domain.model.toTicket
import kotlinx.coroutines.tasks.await

class TicketRepository : TicketDataSource {

    val firebaseDatabase =
        FirebaseDatabase.getInstance("https://weighbridge-d20ca-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val ticketsRef = firebaseDatabase.getReference("tickets")

    override suspend fun getAllTickets(sort: SortBy): ResponseState<List<Ticket?>> {
        try {
            val tickets = ticketsRef.get().await().children.map { snapShot ->
                snapShot.toTicket()
            }.let {
                when (sort) {
                    SortBy.ASC -> it.sortedByDescending { it.date } // oldest first
                    SortBy.DESC -> it.sortedBy { it.date } // newest first
                }
            }
            return ResponseState.Success(tickets)
        } catch (exception: Exception) {
            return ResponseState.Failed(exception.message)
        }
    }

    override suspend fun getTicket(id: String): ResponseState<Ticket?> {
        try {
            val snapshot = ticketsRef.child(id).get().await()
            val ticket = snapshot.toTicket()
            return ResponseState.Success(ticket)
        } catch (exception: Exception) {
            return ResponseState.Failed(exception.message)
        }
    }

    override suspend fun createTicket(ticket: Ticket): ResponseState<Unit> {

        val id = ticketsRef.push().key.toString()
        ticket.createdAt = System.currentTimeMillis()
        ticket.updatedAt = System.currentTimeMillis()
        ticket.id = id
        ticket.time = DateUtil.getCurrentTime()

        return try {
            ticketsRef.child(id).setValue(ticket).await()
            ResponseState.Success(Unit)
        } catch (exception: Exception) {
            ResponseState.Failed(exception.message)
        }

    }

    override suspend fun updateTicket(ticket: Ticket): ResponseState<Unit> {

        ticket.updatedAt = System.currentTimeMillis()

        return try {
            if (ticket.id != null) {
                ticketsRef.child(ticket.id!!).updateChildren(ticket.toMap()).await()
                ResponseState.Success(Unit)
            } else {
                ResponseState.Failed("data not found")
            }
        } catch (exception: Exception) {
            ResponseState.Failed(exception.message)
        }

    }

    override suspend fun deleteTicket(id: String): ResponseState<Unit> {
        return try {
            if (id != null) {
                ticketsRef.child(id).removeValue().await()
                ResponseState.Success(Unit)
            } else {
                ResponseState.Failed("data not found")
            }
        } catch (exception: Exception) {
            ResponseState.Failed(exception.message)
        }
    }

    private fun setLog(msg: String) {
        Log.e("repository", msg)
    }

}