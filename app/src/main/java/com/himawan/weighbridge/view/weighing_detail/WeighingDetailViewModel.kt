package com.himawan.weighbridge.view.weighing_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.domain.model.Ticket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WeighingDetailViewModel(
    private val ticketDataSource: TicketDataSource
): ViewModel() {

    private val _tickets = MutableLiveData<Ticket?>(null)
    val tickets: Flow<Ticket?> = _tickets.asFlow()

    fun getDetailTicket(id: String) {

        viewModelScope.launch {
            val response = ticketDataSource.getTicket(id)

            when (response) {
                is ResponseState.Success -> {
                    _tickets.value = response.data
                }

                is ResponseState.Failed -> {
                }
            }

        }

    }

}