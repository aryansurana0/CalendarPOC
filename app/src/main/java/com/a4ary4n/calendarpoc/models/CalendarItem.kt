package com.a4ary4n.calendarpoc.models

data class CalendarItem(
    val id: Long,
    val name: String,
    val displayName: String,
    val visibility: Boolean,
    val syncEvents: Boolean,
    val accountName: String,
    val accountType: String,
    val calendarColor: Int,
    val ownerAccount: String,
    val calendarLocation: String?,
    val calendarAccessLevel: Int
)
