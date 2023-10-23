package com.ufms.nes.features.template.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ufms.nes.R

@Composable
fun OutlinedBoxClickable(
    onClick: () -> Unit
) {
    val stroke = Stroke(
        width = 3f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
    )

    Box(
        Modifier
            .fillMaxWidth()
            .height(height = 50.dp)
            .drawBehind {
                drawRoundRect(color = Color.DarkGray, style = stroke)
            }
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.DarkGray
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.add),
                color = Color.DarkGray
            )
        }
    }
}
