package com.a4ary4n.calendarpoc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a4ary4n.calendarpoc.databinding.ItemCalendarBinding
import com.a4ary4n.calendarpoc.models.CalendarItem

class AdapterCalendars(
    private val calendars: List<CalendarItem>,
    private val onClick: (calendarId: Long?) -> Unit
) : RecyclerView.Adapter<AdapterCalendars.CalendarsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarsViewHolder {
        return CalendarsViewHolder(
            ItemCalendarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CalendarsViewHolder,
        position: Int
    ) {
        val item = calendars[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int = calendars.size

    inner class CalendarsViewHolder(binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val viewColor = binding.viewColor
        val tvId = binding.tvId
        val tvAccountName = binding.tvAccountName
        val tvAccountType = binding.tvAccountType
        val tvOwnerAccount = binding.tvOwnerAccount
        val tvCalendarLocation = binding.tvCalendarLocation
        val tvCalendarAccessLevel = binding.tvCalendarAccessLevel
        val tvName = binding.tvName
        val tvDisplayName = binding.tvDisplayName
        val tvVisibility = binding.tvVisibility
        val tvSyncEvents = binding.tvSyncEvents

        fun bind(calendarItem: CalendarItem) {
            viewColor.setBackgroundColor(calendarItem.calendarColor)
            tvId.text = calendarItem.id.toString()
            tvAccountName.text = calendarItem.accountName
            tvAccountType.text = calendarItem.accountType
            tvOwnerAccount.text = calendarItem.ownerAccount
            tvCalendarLocation.text = calendarItem.calendarLocation
            tvCalendarAccessLevel.text = calendarItem.calendarAccessLevel.toString()
            tvName.text = calendarItem.name
            tvDisplayName.text = calendarItem.displayName
            tvVisibility.text = "visibility: ${calendarItem.visibility}"
            tvSyncEvents.text = "syncEvents: ${calendarItem.syncEvents}"

            root.setOnClickListener {
                onClick(calendarItem.id)
            }
        }
    }
}