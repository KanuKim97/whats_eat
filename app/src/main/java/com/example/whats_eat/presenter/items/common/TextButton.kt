package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier,
    btnTextContent: String,
    btnColors: Color,
    btnElevation: Dp,
    textColor: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = ShapeDefaults.Large,
        colors = ButtonDefaults.buttonColors(btnColors),
        elevation = ButtonDefaults.buttonElevation(btnElevation),
        content = {
            Text(
                text = btnTextContent,
                color = textColor,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewButtonComponent() {
    TextButton(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        btnTextContent = "로그인",
        btnColors = Color.White,
        btnElevation = 10.dp,
        fontSize = 20.sp,
        textColor = Color.Black,
        fontWeight = FontWeight.Bold
    )
}