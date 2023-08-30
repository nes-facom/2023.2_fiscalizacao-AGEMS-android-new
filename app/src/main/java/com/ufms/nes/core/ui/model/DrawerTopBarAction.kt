package com.ufms.nes.core.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DrawerTopBarAction(
    @DrawableRes val icon: Int,
    @StringRes val description: Int,
    val onClick: () -> Unit
)
