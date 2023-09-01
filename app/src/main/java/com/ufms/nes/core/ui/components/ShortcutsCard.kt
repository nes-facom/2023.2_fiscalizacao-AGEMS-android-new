package com.ufms.nes.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ufms.nes.R

@Composable
fun ShortcutsCard(
    modifier: Modifier = Modifier,
    onCardClickable: () -> Unit,
    icon: ImageVector,
    @StringRes label: Int
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .padding(10.dp)
            .clickable { onCardClickable() },
        shadowElevation = 10.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = icon,
                contentDescription = stringResource(id = R.string.tab_model),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(text = stringResource(id = label), style = MaterialTheme.typography.titleMedium)
        }
    }
}
