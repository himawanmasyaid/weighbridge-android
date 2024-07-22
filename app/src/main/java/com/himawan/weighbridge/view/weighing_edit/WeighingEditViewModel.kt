package com.himawan.weighbridge.view.weighing_edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.himawan.weighbridge.common.DateUtil
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.data.state.ViewState
import com.himawan.weighbridge.domain.model.Ticket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WeighingEditViewModel(
    private val ticketDataSource: TicketDataSource
) : ViewModel() {

    private val _tickets = MutableLiveData<Ticket?>(null)
    val tickets: Flow<Ticket?> = _tickets.asFlow()
    val updateTicketState = MutableLiveData<ViewState<Ticket>>()

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

    fun updateTicket(ticket: Ticket) {

        updateTicketState.postValue(ViewState.Loading())

        viewModelScope.launch {

            try {

                if (ticket.driverName.isEmpty() || ticket.licenseNumber.isEmpty() ||
                    ticket.inboundWeight == 0 || ticket.outboundWeight == 0
                ) {
                    updateTicketState.postValue(ViewState.Error("Harap lengkapi data"))
                } else if (ticket.inboundWeight < ticket.outboundWeight) {
                    updateTicketState.postValue(ViewState.Error("Data muatan tidak sesuai"))
                } else {

                    val netWeight = ticket.inboundWeight - ticket.outboundWeight
                    ticket.netWeight = netWeight

                    val response = ticketDataSource.updateTicket(ticket)
                    when (response) {
                        is ResponseState.Success -> {
                            updateTicketState.postValue(ViewState.Success(ticket))
                        }

                        is ResponseState.Failed -> {
                            updateTicketState.postValue(ViewState.Error("Gagal"))
                        }
                    }

                }

            } catch (error: Exception) {
                updateTicketState.postValue(ViewState.Error(error.message))
            }
        }

    }
}