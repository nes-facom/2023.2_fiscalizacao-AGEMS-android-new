package com.ufms.nes.features.form.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.DrawerTopBar
import com.ufms.nes.features.form.util.Form
import com.ufms.nes.features.form.viewmodel.FormViewModel
import com.ufms.nes.features.form.util.ItemForm
import com.ufms.nes.features.form.util.ErrorMessage
import com.ufms.nes.features.form.util.LoadingNextPageItem
import com.ufms.nes.features.form.util.PageLoader

@Composable
fun FormsScreen(
    viewModel: FormViewModel = hiltViewModel(),
    drawerState: DrawerState,
    onFloatingButtonClick: () -> Unit
) {
    val formPagingItems: LazyPagingItems<Form> = viewModel.formsState.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            DrawerTopBar(drawerState = drawerState, title = R.string.tab_form)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFloatingButtonClick() },
                backgroundColor = MaterialTheme.colorScheme.primary,
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            item { Spacer(modifier = Modifier.padding(4.dp)) }
            if (formPagingItems.itemCount == 0) {
                item {
                    Text(
                        text = stringResource(id = R.string.no_items_found),
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            items(formPagingItems.itemCount) { index ->
                ItemForm(
                    form = formPagingItems[index]!!,
                    onModelClick = {}
                )
            }
            formPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = R.string.load_failed.toString()
                            )
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }
    }
}