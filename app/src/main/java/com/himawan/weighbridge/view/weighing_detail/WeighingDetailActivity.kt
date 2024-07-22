package com.himawan.weighbridge.view.weighing_detail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himawan.weighbridge.domain.model.Ticket
import com.himawan.weighbridge.ui.composable.ToolbarComposable
import com.himawan.weighbridge.ui.theme.LineColor
import com.himawan.weighbridge.ui.theme.TextStyles
import com.himawan.weighbridge.view.weighing_create.WeighingCreateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeighingDetailActivity : ComponentActivity() {

    val viewModel by viewModel<WeighingDetailViewModel>()

    private var ticketId: String = ""

    companion object {
        const val ID_ARG = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()

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

    @Composable
    fun InitView() {

        Scaffold(
            topBar = {
                ToolbarComposable(title = "Detail", onClickListener = {
                    finish()
                })
            },
        ) {

            val ticket = viewModel.tickets.collectAsState(null).value

            if (ticket != null) {
                DetailView(
                    ticket, modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(it)
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp)
                )
            }

        }

    }

    @Composable
    fun DetailView(ticket: Ticket, modifier: Modifier) {


        Column(
            modifier = modifier
        ) {

            Text(
                text = "Nomor Tiket",
                style = TextStyles.textParagraph2
            )

            Text(
                text = "${ticket.id}",
                style = TextStyles.textParagraph1Bold
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Nama Supir",
                style = TextStyles.textParagraph2
            )

            Text(
                text = "${ticket.driverName}",
                style = TextStyles.textParagraph1Bold
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Berat Bersih Muatan",
                style = TextStyles.textParagraph2
            )

            Text(
                text = "${ticket.netWeight}",
                style = TextStyles.textParagraph1Bold
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(LineColor)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Plat Nomor",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = "${ticket.licenseNumber.uppercase()}",
                    style = TextStyles.textParagraph2Medium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Berat Masuk",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = "${ticket.inboundWeight} TON",
                    style = TextStyles.textParagraph2Medium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Berat Keluar",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = "${ticket.outboundWeight} TON",
                    style = TextStyles.textParagraph2Medium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Tanggal Truk Masuk",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = "${ticket.date}",
                    style = TextStyles.textParagraph2Medium
                )
            }

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeightDetailPreview() {

    WeighingDetailActivity().DetailView(
        Ticket(
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
        ),
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp)
    )

}