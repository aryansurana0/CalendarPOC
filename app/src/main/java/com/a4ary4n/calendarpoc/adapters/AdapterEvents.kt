package com.a4ary4n.calendarpoc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a4ary4n.calendarpoc.databinding.ItemEventBinding
import com.a4ary4n.calendarpoc.models.EventItem
import com.a4ary4n.calendarpoc.utils.AppUtil.y4_M2_d2_hh_mm_ss
import java.util.Date

class AdapterEvents(
    private val events: List<EventItem>,
    private val onClick: (eventId: Long?) -> Unit
) : RecyclerView.Adapter<AdapterEvents.EventsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {
        return EventsViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: EventsViewHolder,
        position: Int
    ) {
        val item = events[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int = events.size

    inner class EventsViewHolder(binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val viewColor = binding.viewColor
        val tvId = binding.tvId
        val tvTitle = binding.tvTitle
        val tvOrganizer = binding.tvOrganizer
        val tvEventLocation = binding.tvEventLocation
        val tvDescription = binding.tvDescription
        val tvDtStart = binding.tvDtStart
        val tvDtEnd = binding.tvDtEnd
        val tvEventTimezone = binding.tvEventTimezone
        val tvDuration = binding.tvDuration
        val tvAllDay = binding.tvAllDay
        val tvAvailability = binding.tvAvailability
        val tvRrule = binding.tvRrule

        fun bind(eventItem: EventItem) {
            eventItem.eventColor?.let { viewColor.setBackgroundColor(it) }
            tvId.text = eventItem.id?.toString()
            tvTitle.text = eventItem.title
            tvOrganizer.text = eventItem.organizer
            tvEventLocation.text = eventItem.eventLocation
            tvDescription.text = eventItem.description

            val start = eventItem.dtStart?.let { y4_M2_d2_hh_mm_ss.format(Date(it)) }
            val end = eventItem.dtEnd?.let { y4_M2_d2_hh_mm_ss.format(Date(it)) }
            tvDtStart.text = start
            tvDtEnd.text = end

            tvEventTimezone.text = eventItem.eventTimezone
            tvDuration.text = eventItem.duration
            tvAllDay.text = "AllDay: ${eventItem.allDay}"
            tvAvailability.text = "Availability: ${eventItem.availability}"
            tvRrule.text = eventItem.rrule

            root.setOnClickListener {
                onClick(eventItem.id)
            }
        }
    }
}