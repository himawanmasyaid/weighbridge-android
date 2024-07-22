package com.himawan.weighbridge.view.weighing_create

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.himawan.weighbridge.common.DateUtil
import com.himawan.weighbridge.data.DateFormat
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.data.state.ResponseState
import com.himawan.weighbridge.data.state.ViewState
import com.himawan.weighbridge.domain.model.Ticket
import kotlinx.coroutines.launch
import java.util.UUID

class WeighingCreateViewModel(
    private val ticketDataSource: TicketDataSource
) : ViewModel() {

    val createTicketState = MutableLiveData<ViewState<Ticket>>()

    fun createTicket(ticket: Ticket) {

        createTicketState.postValue(ViewState.Loading())

        viewModelScope.launch {

            try {

                if (ticket.driverName.isEmpty() || ticket.licenseNumber.isEmpty() ||
                    ticket.inboundWeight == 0 || ticket.outboundWeight == 0
                ) {
                    createTicketState.postValue(ViewState.Error("Harap lengkapi data"))
                } else if (ticket.inboundWeight < ticket.outboundWeight) {
                    createTicketState.postValue(ViewState.Error("Data muatan tidak sesuai"))
                } else {

                    val netWeight = ticket.inboundWeight - ticket.outboundWeight
                    ticket.netWeight = netWeight

                    val response = ticketDataSource.createTicket(ticket)
                    when (response) {
                        is ResponseState.Success -> {
                            createTicketState.postValue(ViewState.Success(ticket))
                        }

                        is ResponseState.Failed -> {
                            createTicketState.postValue(ViewState.Error("Gagal"))
                        }
                    }

                }

            } catch (error: Exception) {
                createTicketState.postValue(ViewState.Error(error.message))
            }
        }

    }

}