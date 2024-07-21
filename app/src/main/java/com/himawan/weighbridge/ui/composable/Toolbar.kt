package com.himawan.weighbridge.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.himawan.weighbridge.ui.theme.TextStyles
import com.himawan.weighbridge.view.weighing_create.WeighingCreateActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarComposable(title: String, onClickListener: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title = {
            Text(
                text = title,
                style = TextStyles.textParagraph1Bold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        modifier = Modifier.background(Color.White),
        navigationIcon = {
            IconButton(onClick = onClickListener) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
    )
}

//@Preview(showBackground = true)
//@Composable
//fun ToolbarPreview() {
//    ToolbarComposable("Penimbangan", {})
//}