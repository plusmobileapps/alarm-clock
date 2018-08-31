package com.plusmobileapps.clock.timer.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.plusmobileapps.clock.DataBindingViewHolder
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.databinding.ViewHolderTimerBinding

class TimerDiffCallBack : DiffUtil.ItemCallback<Timer>() {
    override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
        return oldItem == newItem
    }
}

class TimerAdapter(private val viewModel: TimerViewModel)
    : ListAdapter<Timer, DataBindingViewHolder>(TimerDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderTimerBinding>(inflater, R.layout.view_holder_timer, parent, false)
        return DataBindingViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) = holder.bind(getItem(position))
}