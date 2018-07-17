package com.plusmobileapps.clock.timer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.data.timer.Timer

interface TimerItemListener {
    fun timerToggled(position: Int)
    fun timerReset(position: Int)
    fun timerItemDeleted(position: Int)
}

class TimerDiffCallBack : DiffUtil.ItemCallback<Timer>() {
    override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
        return oldItem == newItem
    }
}

class TimerAdapter(private val itemListener: TimerItemListener)
    : ListAdapter<Timer, TimerAdapter.TimerViewHolder>(TimerDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_timer, parent, false)
        return TimerViewHolder(view, itemListener)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) = holder.bind(getItem(position))

    class TimerViewHolder(itemView: View, itemListener: TimerItemListener)  : RecyclerView.ViewHolder(itemView) {
        private val deleteButton: Button = itemView.findViewById(R.id.button_delete)
        private val resetButton: Button = itemView.findViewById(R.id.button_reset)
        private val fab: FloatingActionButton = itemView.findViewById(R.id.fab)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        lateinit var mTimer: Timer
            private set

        init {
            deleteButton.setOnClickListener {
                itemListener.timerItemDeleted(adapterPosition)
            }
            resetButton.setOnClickListener {
                itemListener.timerReset(adapterPosition)
            }
            fab.setOnClickListener {
                itemListener.timerToggled(adapterPosition)
            }
        }

        fun bind(timer: Timer) {
            mTimer = timer

        }

    }
}