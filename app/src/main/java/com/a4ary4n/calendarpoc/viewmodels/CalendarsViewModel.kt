package com.a4ary4n.calendarpoc.viewmodels

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.CalendarContract
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.AndroidViewModel
import com.a4ary4n.calendarpoc.models.CalendarItem
import com.a4ary4n.calendarpoc.models.EventItem
import com.a4ary4n.calendarpoc.models.InstanceItem

class CalendarsViewModel(application: Application) : AndroidViewModel(application) {

    fun getCalendars(): List<CalendarItem> {

        val CALENDARS_EVENT_PROJECTION: Array<String> = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.SYNC_EVENTS,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.OWNER_ACCOUNT,
            CalendarContract.Calendars.CALENDAR_LOCATION,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
        )

        // The indices for the projection array above.
        val PROJECTION__ID_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars._ID)
        val PROJECTION_NAME_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.NAME)
        val PROJECTION_CALENDAR_DISPLAY_NAME_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)
        val PROJECTION_VISIBLE_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.VISIBLE)
        val PROJECTION_SYNC_EVENTS_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.SYNC_EVENTS)
        val PROJECTION_ACCOUNT_NAME_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.ACCOUNT_NAME)
        val PROJECTION_ACCOUNT_TYPE_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.ACCOUNT_TYPE)
        val PROJECTION_CALENDAR_COLOR_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.CALENDAR_COLOR)
        val PROJECTION_OWNER_ACCOUNT_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.OWNER_ACCOUNT)
        val PROJECTION_CALENDAR_LOCATION_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.CALENDAR_LOCATION)
        val PROJECTION_CALENDAR_ACCESS_LEVEL_INDEX =
            CALENDARS_EVENT_PROJECTION.indexOf(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL)


        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val selection: String = ""
//        val selection: String = "(" +
//                "(${CalendarContract.Calendars.ACCOUNT_NAME} = ?)" +
//                " AND (${CalendarContract.Calendars.ACCOUNT_TYPE} = ?)" +
////                " AND (${CalendarContract.Calendars.OWNER_ACCOUNT} = ?)" +
//        ")"
        val selectionArgs: Array<String> = emptyArray<String>()
//        val selectionArgs: Array<String> =
//            arrayOf("surana.aryan88@gmail.com", "com.google"/*, "surana.aryan88@gmail.com"*/)

        val cur = getApplication<Application>().contentResolver.query(
            uri,
            CALENDARS_EVENT_PROJECTION,
            selection,
            selectionArgs,
            null
        )

        // Use the cursor to step through the returned records
        println("outside cursor")
        val items: MutableList<CalendarItem> = mutableListOf()
        while (cur?.moveToNext() == true) {
            println("inside cursor")
            // Get the field values
            val _ID = cur.getLong(PROJECTION__ID_INDEX)
            val NAME = cur.getString(PROJECTION_NAME_INDEX)
            val CALENDAR_DISPLAY_NAME = cur.getString(PROJECTION_CALENDAR_DISPLAY_NAME_INDEX)
            val VISIBLE = cur.getInt(PROJECTION_VISIBLE_INDEX)
            val SYNC_EVENTS = cur.getInt(PROJECTION_SYNC_EVENTS_INDEX)
            val ACCOUNT_NAME = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX)
            val ACCOUNT_TYPE = cur.getString(PROJECTION_ACCOUNT_TYPE_INDEX)
            val CALENDAR_COLOR = cur.getInt(PROJECTION_CALENDAR_COLOR_INDEX)
            val OWNER_ACCOUNT = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX)
            val CALENDAR_LOCATION = cur.getString(PROJECTION_CALENDAR_LOCATION_INDEX)
            val CALENDAR_ACCESS_LEVEL = cur.getInt(PROJECTION_CALENDAR_ACCESS_LEVEL_INDEX)


            // Do something with the values...
            val item = CalendarItem(
                id = _ID,
                name = NAME,
                displayName = CALENDAR_DISPLAY_NAME,
                visibility = VISIBLE == 1,
                syncEvents = SYNC_EVENTS == 1,
                accountName = ACCOUNT_NAME,
                accountType = ACCOUNT_TYPE,
                calendarColor = CALENDAR_COLOR,
                ownerAccount = OWNER_ACCOUNT,
                calendarLocation = CALENDAR_LOCATION,
                calendarAccessLevel = CALENDAR_ACCESS_LEVEL
            )
            items.add(item)
//            println(
//                "_ID: $_ID | " +
//                        "NAME: $NAME | " +
//                        "CALENDAR_DISPLAY_NAME: $CALENDAR_DISPLAY_NAME | " +
//                        "VISIBLE: $VISIBLE | " +
//                        "SYNC_EVENTS: $SYNC_EVENTS | " +
//                        "ACCOUNT_NAME: $ACCOUNT_NAME | " +
//                        "ACCOUNT_TYPE: $ACCOUNT_TYPE | " +
//                        "CALENDAR_COLOR: $CALENDAR_COLOR | " +
//                        "OWNER_ACCOUNT: $OWNER_ACCOUNT | " +
//                        "CALENDAR_LOCATION: $CALENDAR_LOCATION | " +
//                        "CALENDAR_ACCESS_LEVEL: $CALENDAR_ACCESS_LEVEL | "
//            )
        }
        cur?.close()

        return items.toList()
    }


    fun getEvents(calendarId: Long? = null): List<EventItem> {


        val EVENTS_EVENT_PROJECTION: Array<String> = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.ORGANIZER,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.EVENT_LOCATION,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.EVENT_COLOR,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_TIMEZONE,
            CalendarContract.Events.DURATION,
            CalendarContract.Events.ALL_DAY,
            CalendarContract.Events.AVAILABILITY,
            CalendarContract.Events.RRULE,
        )

        val PROJECTION__ID = EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events._ID)
        val PROJECTION_TITLE = EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.TITLE)
        val PROJECTION_ORGANIZER =
            EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.ORGANIZER)
        val PROJECTION_EVENT_LOCATION =
            EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.EVENT_LOCATION)
        val PROJECTION_DESCRIPTION =
            EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.DESCRIPTION)
        val PROJECTION_EVENT_COLOR =
            EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.EVENT_COLOR)
        val PROJECTION_DTSTART = EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.DTSTART)
        val PROJECTION_DTEND = EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.DTEND)
        val PROJECTION_EVENT_TIMEZONE =
            EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.EVENT_TIMEZONE)
        val PROJECTION_DURATION = EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.DURATION)
        val PROJECTION_ALL_DAY = EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.ALL_DAY)
        val PROJECTION_AVAILABILITY =
            EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.AVAILABILITY)
        val PROJECTION_RRULE = EVENTS_EVENT_PROJECTION.indexOf(CalendarContract.Events.RRULE)


        val uri: Uri = CalendarContract.Events.CONTENT_URI
        var selection: String = ""
        val selectionArgs: ArrayList<String> = arrayListOf()
        calendarId?.let {
            selection += "(${CalendarContract.Events.CALENDAR_ID} = ?)"
            selectionArgs.add(it.toString())
        }

//        val selection: String = "(" +
//                "(${CalendarContract.Calendars.ACCOUNT_NAME} = ?)" +
//                " AND (${CalendarContract.Calendars.ACCOUNT_TYPE} = ?)" +
////                " AND (${CalendarContract.Calendars.OWNER_ACCOUNT} = ?)" +
//        ")"

        val cur = getApplication<Application>().contentResolver.query(
            uri,
            EVENTS_EVENT_PROJECTION,
            selection,
            selectionArgs.toTypedArray(),
            null
        )

        // Use the cursor to step through the returned records
        println("outside cursor")
        val items: MutableList<EventItem> = mutableListOf()
        while (cur?.moveToNext() == true) {
            println("inside cursor")
            // Get the field values
            val _ID = cur.getLongOrNull(PROJECTION__ID)
            val ORGANIZER = cur.getStringOrNull(PROJECTION_ORGANIZER)
            val TITLE = cur.getStringOrNull(PROJECTION_TITLE)
            val EVENT_LOCATION = cur.getStringOrNull(PROJECTION_EVENT_LOCATION)
            val DESCRIPTION = cur.getStringOrNull(PROJECTION_DESCRIPTION)
            val EVENT_COLOR = cur.getIntOrNull(PROJECTION_EVENT_COLOR)
            val DTSTART = cur.getLongOrNull(PROJECTION_DTSTART)
            val DTEND = cur.getLongOrNull(PROJECTION_DTEND)
            val EVENT_TIMEZONE = cur.getStringOrNull(PROJECTION_EVENT_TIMEZONE)
            val DURATION = cur.getStringOrNull(PROJECTION_DURATION)
            val ALL_DAY = cur.getIntOrNull(PROJECTION_ALL_DAY)
            val AVAILABILITY = cur.getIntOrNull(PROJECTION_AVAILABILITY)
            val RRULE = cur.getStringOrNull(PROJECTION_RRULE)


            // Do something with the values...
            val item = EventItem(
                id = _ID,
                organizer = ORGANIZER,
                title = TITLE,
                eventLocation = EVENT_LOCATION,
                description = DESCRIPTION,
                eventColor = EVENT_COLOR,
                dtStart = DTSTART,
                dtEnd = DTEND,
                eventTimezone = EVENT_TIMEZONE,
                duration = DURATION,
                allDay = ALL_DAY,
                availability = AVAILABILITY,
                rrule = RRULE
            )
            items.add(item)
//            println(
//                "CALENDAR_ID: $CALENDAR_ID | " +
//                        "ORGANIZER: $ORGANIZER | " +
//                        "TITLE: $TITLE | " +
//                        "EVENT_LOCATION: $EVENT_LOCATION | " +
//                        "DESCRIPTION: $DESCRIPTION | " +
//                        "EVENT_COLOR: $EVENT_COLOR | " +
//                        "DTSTART: $DTSTART | " +
//                        "DTEND: $DTEND | " +
//                        "EVENT_TIMEZONE: $EVENT_TIMEZONE | " +
//                        "DURATION: $DURATION | " +
//                        "ALL_DAY: $ALL_DAY | " +
//                        "AVAILABILITY: $AVAILABILITY | "
//            )
        }
        cur?.close()

        return items.toList()
//        return emptyList()
    }

    fun getInstances(
        eventId: Long? = null,
        startMillis: Long,
        endMillis: Long
    ): List<InstanceItem> {
        println("getInstances eventId: $eventId, startMillis: $startMillis, endMillis: $endMillis")
        val EVENTS_PROJECTION: Array<String> = arrayOf(
            CalendarContract.Instances._ID,
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END,
            CalendarContract.Instances.START_DAY,
            CalendarContract.Instances.START_MINUTE,
            CalendarContract.Instances.END_DAY,
            CalendarContract.Instances.END_MINUTE,
        )

        val PROJECTION__ID_INDEX = EVENTS_PROJECTION.indexOf(CalendarContract.Instances._ID)
        val PROJECTION_EVENT_ID_INDEX =
            EVENTS_PROJECTION.indexOf(CalendarContract.Instances.EVENT_ID)
        val PROJECTION_TITLE_INDEX = EVENTS_PROJECTION.indexOf(CalendarContract.Instances.TITLE)
        val PROJECTION_BEGIN_INDEX = EVENTS_PROJECTION.indexOf(CalendarContract.Instances.BEGIN)
        val PROJECTION_END_INDEX = EVENTS_PROJECTION.indexOf(CalendarContract.Instances.END)
        val PROJECTION_START_DAY_INDEX =
            EVENTS_PROJECTION.indexOf(CalendarContract.Instances.START_DAY)
        val PROJECTION_START_MINUTE_INDEX =
            EVENTS_PROJECTION.indexOf(CalendarContract.Instances.START_MINUTE)
        val PROJECTION_END_DAY_INDEX = EVENTS_PROJECTION.indexOf(CalendarContract.Instances.END_DAY)
        val PROJECTION_END_MINUTE_INDEX =
            EVENTS_PROJECTION.indexOf(CalendarContract.Instances.END_MINUTE)

        val builder: Uri.Builder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(builder, startMillis)
        ContentUris.appendId(builder, endMillis)

        var selection: String = ""
        var selectionArgs: ArrayList<String> = arrayListOf()
        eventId?.let {
            selection += "(${CalendarContract.Instances.EVENT_ID} = ?)"
            selectionArgs.add(it.toString())
        }
        println("selection: $selection")
        println("selectionArgs: $selectionArgs")

        val cur = getApplication<Application>().contentResolver.query(
            builder.build(),
            EVENTS_PROJECTION,
            selection,
            selectionArgs.toTypedArray(),
            null
        )

        println("outside cursor")
        val items: MutableList<InstanceItem> = mutableListOf()
        while (cur?.moveToNext() == true) {
            println("inside cursor")
            val _ID = cur.getLongOrNull(PROJECTION__ID_INDEX)
            val EVENT_ID = cur.getLongOrNull(PROJECTION_EVENT_ID_INDEX)
            val TITLE = cur.getStringOrNull(PROJECTION_TITLE_INDEX)
            val BEGIN = cur.getLongOrNull(PROJECTION_BEGIN_INDEX)
            val END = cur.getLongOrNull(PROJECTION_END_INDEX)
            val START_DAY = cur.getIntOrNull(PROJECTION_START_DAY_INDEX)
            val START_MINUTE = cur.getIntOrNull(PROJECTION_START_MINUTE_INDEX)
            val END_DAY = cur.getIntOrNull(PROJECTION_END_DAY_INDEX)
            val END_MINUTE = cur.getIntOrNull(PROJECTION_END_MINUTE_INDEX)

            val item = InstanceItem(
                id = _ID,
                eventId = EVENT_ID,
                title = TITLE,
                beginTimeInMillis = BEGIN,
                endTimeInMillis = END,
                startDay = START_DAY,
                startMinute = START_MINUTE,
                endDay = END_DAY,
                endMinute = END_MINUTE,
            )
            items.add(item)

            println(
                "_ID: $_ID | " +
                        "EVENT_ID: $EVENT_ID | " +
                        "TITLE: $TITLE | " +
                        "BEGIN: $BEGIN | " +
                        "END: $END | " +
                        "START_DAY: $START_DAY | " +
                        "START_MINUTE: $START_MINUTE | " +
                        "END_DAY: $END_DAY | " +
                        "END_MINUTE: $END_MINUTE | "
            )
        }
        cur?.close()

        return items.toList()
        return emptyList()
    }

}