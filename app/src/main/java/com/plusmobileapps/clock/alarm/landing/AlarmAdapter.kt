package com.plusmobileapps.clock.alarm.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.data.entities.Alarm

interface AlarmItemListener {
    fun alarmItemClicked(position: Int)
    fun alarmTimeClicked(position: Int)
    fun alarmSwitchToggled(position: Int, isEnabled: Boolean)
}

class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }
}

class AlarmAdapter(private val itemListener: AlarmItemListener)
    : ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(AlarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_alarm, parent, false)
        return AlarmViewHolder(view, itemListener)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) = holder.bind(getItem(position))

    class AlarmViewHolder(itemView: View, itemListener: AlarmItemListener) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val alarmToggle: Switch = itemView.findViewById(R.id.alarm_toggle)
        private val alarmTime: TextView = itemView.findViewById(R.id.edit_time_button)
        lateinit var mAlarm: Alarm
            private set

        init {
            alarmTime.setOnClickListener {
                itemListener.alarmTimeClicked(adapterPosition)
            }
            itemView.setOnClickListener {
                itemListener.alarmItemClicked(adapterPosition)
            }
            alarmToggle.setOnCheckedChangeListener { _, isChecked ->
                itemListener.alarmSwitchToggled(adapterPosition, isChecked)
            }
        }

        fun bind(alarm: Alarm) {
            mAlarm = alarm
            alarmToggle.isChecked = alarm.enabled
            alarmTime.text = alarm.printTime()
        }
    }

}

