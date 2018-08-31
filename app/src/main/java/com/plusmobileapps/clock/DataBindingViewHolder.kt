package com.plusmobileapps.clock

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder(private val binding: ViewDataBinding,
                             private val viewmodel: ViewModel) : RecyclerView.ViewHolder(binding.root) {

    lateinit var data: Any

    fun bind(data: Any) {
        this.data = data
        binding.apply {
            setVariable(BR.data, data)
            setVariable(BR.viewmodel, viewmodel)
            executePendingBindings()
        }
    }

}