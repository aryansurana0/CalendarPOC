package com.a4ary4n.calendarpoc.models

data class InstanceItem(
    val id: Long?,
    val eventId: Long?,
    val title: String?,
    val beginTimeInMillis: Long?,
    val endTimeInMillis: Long?,
    val startDay: Int?,
    val startMinute: Int?,
    val endDay: Int?,
    val endMinute: Int?,
)
