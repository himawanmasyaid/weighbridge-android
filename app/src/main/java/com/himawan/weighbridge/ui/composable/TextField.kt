package com.himawan.weighbridge.ui.composable

import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himawan.weighbridge.R
import com.himawan.weighbridge.ui.theme.HintColor
import com.himawan.weighbridge.ui.theme.LineColor
import com.himawan.weighbridge.ui.theme.TextStyles

@Composable
fun OutlinedTextFieldComposable(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        autoCorrect = false,
    ),
    singleLine: Boolean = true
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyles.textParagraph2Medium,
        label = {
            Text(
                if (value.isEmpty()) label else "",
                style = TextStyles.textParagraph2Medium,
                color = HintColor
            )
        },
        shape = RoundedCornerShape(4.dp),
        singleLine = singleLine,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = HintColor,
            focusedBorderColor = HintColor,
            unfocusedBorderColor = HintColor,
        ),
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun OutlinedTextSelectComposable(
    label: String,
    onClickListener: () -> Unit
) {

    Row(
        modifier = Modifier
            .clickable {
                onClickListener
            }
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(color = Color.White)
            .border(
                width = 1.dp,
                color = LineColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {

        Text(
            text = label,
            style = TextStyles.textParagraph2Medium
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_date_gray),
            contentDescription = "icon",
            modifier = Modifier.size(22.dp),
        )

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextFieldPreview() {

    var outboundWeight by rememberSaveable { mutableStateOf("") }

//    OutlinedTextSelectComposable("Input data disini", {})

//    OutlinedTextFieldComposable(
//        value = outboundWeight,
//        onValueChange = { outboundWeight = it },
//        label = "Input berat muatan (TON)",
//        keyboardOptions = KeyboardOptions.Default.copy(
//            keyboardType = KeyboardType.Number,
//            autoCorrect = false,
//        )
//    )
}