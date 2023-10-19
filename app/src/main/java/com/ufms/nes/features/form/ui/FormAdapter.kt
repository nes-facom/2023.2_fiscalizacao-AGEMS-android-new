package com.ufms.nes.features.form.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ufms.nes.features.form.data.Form

/**
 * Adapter for an [Article] [List].
 */
class ArticleAdapter : PagingDataAdapter<Form, FormViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder =
        FormViewHolder(
            FormViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Form>() {
            override fun areItemsTheSame(oldItem: Form, newItem: Form): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Form, newItem: Form): Boolean =
                oldItem == newItem
        }
    }
}
