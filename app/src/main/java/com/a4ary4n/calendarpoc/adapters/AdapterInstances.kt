package com.a4ary4n.calendarpoc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a4ary4n.calendarpoc.databinding.ItemInstanceBinding
import com.a4ary4n.calendarpoc.models.InstanceItem
import com.a4ary4n.calendarpoc.utils.AppUtil.y4_M2_d2_hh_mm_ss
import java.util.Date

class AdapterInstances(
    private val instances: List<InstanceItem>,
) : RecyclerView.Adapter<AdapterInstances.InstancesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InstancesViewHolder {
        return InstancesViewHolder(
            ItemInstanceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: InstancesViewHolder,
        position: Int
    ) {
        val item = instances[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int = instances.size

    inner class InstancesViewHolder(binding: ItemInstanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvId = binding.tvId
        val tvEventId = binding.tvEventId
        val tvTitle = binding.tvTitle
        val tvBeginTime = binding.tvBeginTime
        val tvEndTime = binding.tvEndTime
        val tvStartDay = binding.tvStartDay
        val tvStartMinute = binding.tvStartMinute
        val tvEndDay = binding.tvEndDay
        val tvEndMinute = binding.tvEndMinute

        fun bind(instanceItem: InstanceItem) {
            tvId.text = instanceItem.id?.toString()
            tvEventId.text = instanceItem.eventId?.toString()
            tvTitle.text = instanceItem.title

            val start = instanceItem.beginTimeInMillis?.let { y4_M2_d2_hh_mm_ss.format(Date(it)) }
            val end = instanceItem.endTimeInMillis?.let { y4_M2_d2_hh_mm_ss.format(Date(it)) }
            tvBeginTime.text = start
            tvEndTime.text = end

            tvStartDay.text = instanceItem.startDay.toString()
            tvStartMinute.text = instanceItem.startMinute.toString()
            tvEndDay.text = instanceItem.endDay.toString()
            tvEndMinute.text = instanceItem.endMinute.toString()

        }
    }
}