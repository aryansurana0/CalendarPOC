package com.a4ary4n.calendarpoc.models

data class EventItem(
    val id: Long? = null,
    val organizer: String? = null,
    val title: String? = null,
    val eventLocation: String? = null,
    val description: String? = null,
    val eventColor: Int? = null,
    val dtStart: Long? = null,
    val dtEnd: Long? = null,
    val eventTimezone: String? = null,
    val duration: String? = null,
    val allDay: Int? = null,
    val availability: Int? = null,
    val rrule: String? = null,
)