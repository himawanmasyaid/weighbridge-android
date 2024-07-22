package com.himawan.weighbridge.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.himawan.weighbridge.R
import com.himawan.weighbridge.data.SortBy
import com.himawan.weighbridge.domain.model.Ticket
import com.himawan.weighbridge.ui.composable.ButtonPrimary
import com.himawan.weighbridge.ui.composable.ToolbarComposable
import com.himawan.weighbridge.ui.composable.ToolbarTitleComposable
import com.himawan.weighbridge.ui.theme.PrimaryColor
import com.himawan.weighbridge.ui.theme.TextStyles
import com.himawan.weighbridge.view.weighing_create.WeighingCreateActivity
import com.himawan.weighbridge.view.weighing_detail.WeighingDetailActivity
import com.himawan.weighbridge.view.weighing_edit.WeighingEditActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAllTicket()

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

    @Composable
    fun InitView() {

        Scaffold(
            topBar = {
                ToolbarTitleComposable("Weighbridge")
            },
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // Pengguna harus dapat memfilter dan mengurutkan
                // daftar berdasarkan tanggal penimbangan, nama pengemudi, plat nomor

                var sortBy by rememberSaveable { mutableStateOf(SortBy.ASC) }
                var isExpand by rememberSaveable { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    val sortDesc = if (sortBy == SortBy.ASC) "Terbaru" else "Terlama"

                    Text(
                        text = "Daftar Tiket $sortDesc",
                        style = TextStyles.textParagraph2Medium
                    )

                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.ic_sort),
                            contentDescription = "sort",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    isExpand = !isExpand
                                },
                        )

                        DropdownMenu(
                            modifier = Modifier.width(100.dp),
                            expanded = isExpand,
                            onDismissRequest = { isExpand = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    isExpand = false
                                    if (sortBy != SortBy.ASC) {
                                        sortBy = SortBy.ASC
                                        viewModel.getAllTicket(SortBy.ASC)
                                    }
                                },
                                text = {
                                    Text(
                                        "Terbaru",
                                        style = TextStyles.textParagraph2
                                    )
                                }
                            )

                            DropdownMenuItem(
                                onClick = {
                                    isExpand = false
                                    if (sortBy != SortBy.DESC) {
                                        sortBy = SortBy.DESC
                                        viewModel.getAllTicket(SortBy.DESC)
                                    }
                                },
                                text = {
                                    Text(
                                        "Terlama",
                                        style = TextStyles.textParagraph2
                                    )
                                }
                            )
                        }
                    }

                }

                TicketListView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f)
                )

                ButtonPrimary(
                    text = "Buat Penimbangan",
                    onClick = {
                        startActivity(Intent(this@MainActivity, WeighingCreateActivity::class.java))
                    }
                )

            }
        }

    }

    @Composable
    fun TicketListView(modifier: Modifier = Modifier) {

        val tickets = viewModel.tickets.collectAsState(initial = emptyList()).value
        val isLoading = viewModel.isLoading.observeAsState()

        setLog("tickets : ${tickets.size}")

        Box(modifier = modifier) {

            if (isLoading.value == true) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {

                if (tickets.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ticket Empty",
                            style = TextStyles.textParagraph1Medium
                        )
                    }
                } else {
                    LazyColumn(modifier = modifier) {
                        items(tickets) {
                            if (it != null) {
                                TicketItem(ticket = it)
                            }
                        }

                    }
                }

            }

        }

    }

    @Composable
    fun TicketItem(ticket: Ticket) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color.White) // white background
                .border(width = 1.dp, color = Color(0XFFE0E0E0), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp)
                .padding(top = 18.dp, bottom = 4.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        text = "Nomor Tiket",
                        style = TextStyles.textParagraph2
                    )

                    Text(
                        text = ticket.id ?: "-",
                        style = TextStyles.textParagraph1Bold,
                        fontSize = 22.sp,
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "sort",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val intent = Intent(this@MainActivity, WeighingEditActivity::class.java)
                            intent.putExtra(WeighingEditActivity.ID_ARG, ticket.id)
                            startActivity(intent)
                        },
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        color = Color(0xFFE0E0E0),
                    )
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Supir",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = ticket.driverName,
                    style = TextStyles.textParagraph2Medium
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Plat",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = ticket.licenseNumber.uppercase(),
                    style = TextStyles.textParagraph2Medium
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Berat Bersih",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = "${ticket.netWeight} TON",
                    style = TextStyles.textParagraph2Medium
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Truk Masuk",
                    style = TextStyles.textParagraph2
                )

                Text(
                    text = ticket.date,
                    style = TextStyles.textParagraph2Medium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        // navigate to detail
                        val intent = Intent(this@MainActivity, WeighingDetailActivity::class.java)
                        intent.putExtra(WeighingDetailActivity.ID_ARG, ticket.id!!)
                        startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(top = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                    ),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(
                        "Lihat Detail",
                        style = TextStyles.textParagraph3Bold,
                        color = Color.White
                    )
                }
            }

        }
    }

    @Composable
    fun FilterView() {

    }

    private fun setLog(msg: String) {
        Log.e("ticket", msg)
    }

    @Composable
    fun Dropdown() {

        var expanded by remember { mutableStateOf(false) }
        var selectSortBy by rememberSaveable { mutableStateOf(SortBy.ASC) }

        Column {
            Text(
                text = "Selected: $selectSortBy",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = { selectSortBy = SortBy.ASC },
                    text = { Text("Terlama") }
                )

                DropdownMenuItem(
                    onClick = { selectSortBy = SortBy.DESC },
                    text = { Text("Terbaru") }
                )
            }

            // Clickable area to toggle the dropdown
            Box(modifier = Modifier.size(200.dp)) {
                Text(
                    text = "Click here to expand",
                    color = Color.Blue,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable(onClick = { expanded = true })
                )
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActivityPreview() {

    MainActivity().Dropdown()

//    MainActivity().TicketItem(
//        Ticket(
//            id = "1234",
//            date = "2023-03-15",
//            time = "14:30:00",
//            licenseNumber = "BG 1234 AB",
//            driverName = "Arsad Sapardie",
//            inboundWeight = 10000,
//            outboundWeight = 8000,
//            netWeight = 2000,
//            createdAt = 1678903400,
//            updatedAt = 1678903400
//        )
//    )
}