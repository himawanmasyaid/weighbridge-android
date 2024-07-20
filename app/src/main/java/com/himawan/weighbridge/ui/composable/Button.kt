package com.himawan.weighbridge.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himawan.weighbridge.ui.theme.PrimaryColor
import com.himawan.weighbridge.ui.theme.TextStyles

@Composable
fun ButtonPrimary(
    text: String = "Submit",
    modifier : Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .height(50.dp),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
        ),
        shape = RoundedCornerShape(6.dp),
    ) {
        Text(
            text,
            style = TextStyles.textParagraph1Bold,
            color = Color.White
        )
    }
}