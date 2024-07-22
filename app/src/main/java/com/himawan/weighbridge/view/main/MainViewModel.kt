package com.himawan.weighbridge.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.himawan.weighbridge.data.FirebaseReferences
import com.himawan.weighbridge.data.SortBy
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.domain.model.Ticket
import com.himawan.weighbridge.domain.model.toTicket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(
    private val ticketDataSource: TicketDataSource
) : ViewModel() {

    private val _tickets = MutableLiveData<List<Ticket?>>()
    val tickets: Flow<List<Ticket?>> = _tickets.asFlow()

    private val _loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _loading

    fun getAllTicket(sort: SortBy = SortBy.ASC) {

        _loading.value = true

        viewModelScope.launch {
            val response = ticketDataSource.getAllTickets(sort)

            when (response) {
                is ResponseState.Success -> {
                    _tickets.value = response.data
                    _loading.value = false
                }

                is ResponseState.Failed -> {
                    _loading.value = false
                }
            }
        }

    }

    private fun setLog(msg: String) {
        Log.e("vm", msg)
    }

}