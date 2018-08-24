package com.plusmobileapps.clock.alarm.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.clock.DataBindingViewHolder
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.detail.AlarmDetailFragment.Companion.EXTRA_ALARM_ID
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.databinding.ViewHolderAlarmBinding
import org.jetbrains.anko.bundleOf

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

