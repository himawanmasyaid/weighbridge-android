package com.himawan.weighbridge.view.weighing_edit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himawan.weighbridge.common.DateUtil
import com.himawan.weighbridge.common.toast
import com.himawan.weighbridge.data.DateFormat
import com.himawan.weighbridge.data.state.ViewState
import com.himawan.weighbridge.domain.model.Ticket
import com.himawan.weighbridge.ui.composable.ButtonPrimary
import com.himawan.weighbridge.ui.composable.OutlinedTextFieldComposable
import com.himawan.weighbridge.ui.composable.OutlinedTextSelectComposable
import com.himawan.weighbridge.ui.composable.ToolbarComposable
import com.himawan.weighbridge.ui.theme.HintColor
import com.himawan.weighbridge.ui.theme.LineColor
import com.himawan.weighbridge.ui.theme.PorcelainColor
import com.himawan.weighbridge.ui.theme.TextStyles
import com.himawan.weighbridge.view.main.MainActivity
import com.himawan.weighbridge.view.weighing_detail.WeighingDetailActivity
import com.himawan.weighbridge.view.weighing_detail.WeighingDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeighingEditActivity : ComponentActivity() {

    val viewModel by viewModel<WeighingEditViewModel>()

    private var ticketId: String = ""

    companion object {
        const val ID_ARG = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()
        startObserveData()
        setContent {
            Surface(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(),

                ) {
                InitView()
            }

        }

    }

    private fun getIntentData() {

        ticketId = intent.getStringExtra(ID_ARG) ?: ""

        viewModel.getDetailTicket(ticketId)

    }

    private fun startObserveData() {

        viewModel.updateTicketState.observe(this) {
            when (it) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }

                is ViewState.Error -> {
                    toast(it.message ?: "Gagal")
                }
            }
        }

    }

    @Composable
    fun InitView() {

        Scaffold(
            topBar = {
                ToolbarComposable(title = "Penimbangan", onClickListener = {
                    finish()
                })
            },
        ) {

            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(it)
            ) {

                item {
                    ContentView(
                        modifier = Modifier
                            .fillMaxSize()
                    )

                }

            }
        }
    }

    @Composable
    fun ContentView(
        modifier: Modifier = Modifier
    ) {

        Box(modifier = modifier) {

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp)
            ) {

                val ticket = viewModel.tickets.collectAsState(null).value

                if (ticket != null) {

                    var driverName by rememberSaveable { mutableStateOf(ticket.driverName) }
                    var licenseNumber by rememberSaveable { mutableStateOf(ticket.licenseNumber) }
                    var inboundWeight by rememberSaveable { mutableStateOf(ticket.inboundWeight.toString()) }
                    var outboundWeight by rememberSaveable { mutableStateOf(ticket.outboundWeight.toString()) }
                    var truckEnteringDate by rememberSaveable {
                        mutableStateOf(
                            ticket.date
                        )
                    }

                    Text(
                        text = "Nomor Tiket",
                        style = TextStyles.textParagraph2
                    )

                    Text(
                        text = ticketId,
                        style = TextStyles.textParagraph2Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = LineColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .background(PorcelainColor)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Text(
                        text = "Nama Supir",
                        style = TextStyles.textParagraph2
                    )

                    OutlinedTextFieldComposable(
                        value = driverName,
                        onValueChange = { driverName = it },
                        label = "Input Nama Supir"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Plat Nomor",
                        style = TextStyles.textParagraph2
                    )

                    OutlinedTextFieldComposable(
                        value = licenseNumber,
                        onValueChange = { licenseNumber = it },
                        label = "Input Plat Nomor"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Spacer(
                        modifier = Modifier
                            .background(LineColor)
                            .fillMaxWidth()
                            .height(1.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Muatan Barang",
                        style = TextStyles.textParagraph1Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Berat Masuk (TON)",
                        style = TextStyles.textParagraph2
                    )

                    OutlinedTextFieldComposable(
                        value = inboundWeight,
                        onValueChange = { inboundWeight = it },
                        label = "Input berat muatan (TON)",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            autoCorrect = false,
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Berat Keluar (TON)",
                        style = TextStyles.textParagraph2
                    )

                    OutlinedTextFieldComposable(
                        value = outboundWeight,
                        onValueChange = { outboundWeight = it },
                        label = "Input berat muatan (TON)",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            autoCorrect = false,
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Spacer(
                        modifier = Modifier
                            .background(LineColor)
                            .fillMaxWidth()
                            .height(1.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Masuk Penimbangan",
                        style = TextStyles.textParagraph1Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Tanggal Masuk Penimbangan",
                        style = TextStyles.textParagraph2
                    )

                    OutlinedTextSelectComposable(truckEnteringDate, {

                    })

                    Spacer(modifier = Modifier.height(16.dp))

                    ButtonPrimary(
                        text = "SIMPAN",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(50.dp),
                        onClick = {
                            val ticket = Ticket(
                                id = ticketId,
                                driverName = driverName,
                                licenseNumber = licenseNumber,
                                inboundWeight = if (inboundWeight.isEmpty()) 0 else inboundWeight.toInt(),
                                outboundWeight = if (outboundWeight.isEmpty()) 0 else outboundWeight.toInt(),
                                date = truckEnteringDate
                            )
                            viewModel.updateTicket(ticket)
                        }
                    )

                }

            }

        }

    }

}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun WeightDetailPreview() {
//
//    Text(
//        text = "Umar Syaid Himawan",
//        style = TextStyles.textParagraph2Medium,
//        modifier = Modifier
//            .fillMaxWidth()
//            .border(
//                width = 1.dp,
//                color = LineColor,
//                shape = RoundedCornerShape(4.dp)
//            )
//            .background(PorcelainColor)
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//    )
//
//}