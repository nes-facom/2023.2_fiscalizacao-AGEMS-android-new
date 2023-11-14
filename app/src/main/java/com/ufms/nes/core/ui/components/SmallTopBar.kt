package com.ufms.nes.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopBar(
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.enterAlwaysScrollBehavior(
        topAppBarState
    ),
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    data: TopBarData
) {
    TopAppBar(
        title = {
            Text(text = stringResource(data.title))
        },
        colors = colors,
        navigationIcon = {
            data.navigationIcon?.let {
                IconButton(
                    onClick = { data.onNavigationClick() }) {
                    Icon(
                        imageVector = it,
                        contentDescription = data.navigationIconContentDescription?.let { it1 ->
                            stringResource(it1)
                        }
                    )
                }
            }
        },
        actions = {
            data.iconsActions.forEach { iconData ->
                if (iconData.isVisible) {
                    IconButton(onClick = { iconData.onActionClick() }) {
                        Icon(
                            imageVector = iconData.actionIcon,
                            contentDescription = stringResource(iconData.actionIconContentDescription)
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

data class TopBarData(
    @StringRes val title: Int,
    val iconsActions: List<IconData>,
    val navigationIcon: ImageVector? = null,
    @StringRes val navigationIconContentDescription: Int? = null,
    val onNavigationClick: () -> Unit,
)

data class IconData(
    val actionIcon: ImageVector,
    val onActionClick: () -> Unit,
    val isVisible: Boolean = true,
    @StringRes val actionIconContentDescription: Int
)