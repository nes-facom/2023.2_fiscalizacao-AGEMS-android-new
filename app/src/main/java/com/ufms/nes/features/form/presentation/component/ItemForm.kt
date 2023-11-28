package com.ufms.nes.features.form.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ufms.nes.R
import com.ufms.nes.core.commons.convertLongToFormattedDate
import com.ufms.nes.features.form.data.model.Form

@Composable
fun ItemForm(
    modifier: Modifier = Modifier,
    form: Form,
    onModelClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onModelClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .defaultMinSize(minHeight = 48.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            form.user?.let {
                Text(
                    text = stringResource(R.string.user, it),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = stringResource(R.string.unit, form.unit.toString()),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            form.creationDate?.let {
                Text(
                    text =
                    stringResource(
                        R.string.creation_date,
                        it.convertLongToFormattedDate()
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}