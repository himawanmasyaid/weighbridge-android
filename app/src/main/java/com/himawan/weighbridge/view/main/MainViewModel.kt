package com.himawan.weighbridge.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.himawan.weighbridge.data.FirebaseReferences
import com.himawan.weighbridge.data.source.TicketDataSource
import com.himawan.weighbridge.domain.model.Ticket
import com.himawan.weighbridge.domain.model.toTicket
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val ticketDataSource: TicketDataSource
) : ViewModel() {

    private val _tickets = MutableLiveData<List<Ticket?>>()
    val tickets: Flow<List<Ticket?>> = _tickets.asFlow()

    private val _loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _loading

    val firebaseDatabase = FirebaseDatabase.getInstance("https://weighbridge-d20ca-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val ticketsRef = firebaseDatabase.getReference(FirebaseReferences.TICKETS)

    fun getAllTicket() {

        _loading.value = true

        ticketsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val tickets = dataSnapshot.children.map { it.toTicket() }
                    _tickets.value = tickets
                    _loading.value = false

                } catch (error: Exception) {
                    _loading.value = false
                    setLog("error : ${error.message}")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                _loading.value = false
                setLog("tickets : onCancelled")
            }
        })

    }

    private fun setLog(msg: String) {
        Log.e("vm", msg)
    }

}