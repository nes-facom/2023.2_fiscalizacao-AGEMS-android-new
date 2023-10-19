package com.ufms.nes.features.form.ui

import androidx.recyclerview.widget.RecyclerView

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class FormViewHolder(
    private val binding: FormViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(form: Form) {
        binding.apply {
            binding.title.text = form.title
            binding.description.text = form.description
            binding.created.text = form.createdText
        }
    }
}
