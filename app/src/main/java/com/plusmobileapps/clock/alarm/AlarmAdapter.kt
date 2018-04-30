package com.plusmobileapps.clock.alarm

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.data.entities.Alarm
import kotlinx.android.synthetic.main.view_holder_alarm.view.*


class AlarmAdapter(var alarms: List<Alarm>) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.alarmToggle.isEnabled = alarm.enabled
        holder.alarmTime.text = alarm.printTime()
    }

    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val alarmToggle: Switch = itemView.alarm_toggle
        val alarmTime: TextView = itemView.edit_time_button
    }

}