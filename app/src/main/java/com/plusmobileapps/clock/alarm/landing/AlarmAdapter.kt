package com.plusmobileapps.clock.alarm.landing

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.data.entities.Alarm

interface AlarmItemListener {
    fun alarmItemClicked(position: Int)
    fun alarmTimeClicked(position: Int)
    fun alarmSwitchToggled(position: Int, isEnabled: Boolean)
}

class AlarmAdapter(var alarms: List<Alarm>, private val itemListener: AlarmItemListener) : androidx.recyclerview.widget.RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_alarm, parent, false)
        return AlarmViewHolder(view, itemListener)
    }

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.alarmToggle.isChecked = alarm.enabled
        holder.alarmTime.text = alarm.printTime()
    }

    class AlarmViewHolder(itemView: View, itemListener: AlarmItemListener) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val alarmToggle: Switch = itemView.findViewById(R.id.alarm_toggle)
        val alarmTime: TextView = itemView.findViewById(R.id.edit_time_button)

        init {
            alarmTime.setOnClickListener{
                itemListener.alarmTimeClicked(adapterPosition)
            }
            itemView.setOnClickListener{
                itemListener.alarmItemClicked(adapterPosition)
            }
            alarmToggle.setOnCheckedChangeListener{ _, isChecked ->
                itemListener.alarmSwitchToggled(adapterPosition, isChecked)
            }
        }
    }

}