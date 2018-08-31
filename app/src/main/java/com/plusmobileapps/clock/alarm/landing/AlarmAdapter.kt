package com.plusmobileapps.clock.alarm.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.plusmobileapps.clock.DataBindingViewHolder
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.databinding.ViewHolderAlarmBinding

class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }
}


class AlarmAdapter(private val viewModel: AlarmLandingViewModel)
    : ListAdapter<Alarm, DataBindingViewHolder>(AlarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderAlarmBinding>(inflater, R.layout.view_holder_alarm, parent, false)
        return DataBindingViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) = holder.bind(getItem(position))
}

