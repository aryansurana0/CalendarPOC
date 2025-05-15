# Calendar POC (Proof of Concept)

An Android application demonstrating calendar integration and management using [Android's Calendar Provider API](https://developer.android.com/guide/topics/providers/calendar-provider).

## Features

- 📅 List all available calendars on the device using [CalendarContract.Calendars](https://developer.android.com/reference/android/provider/CalendarContract.Calendars)
- 📆 View calendar events through [CalendarContract.Events](https://developer.android.com/reference/android/provider/CalendarContract.Events)
- 🕒 View calendar event instances via [CalendarContract.Instances](https://developer.android.com/reference/android/provider/CalendarContract.Instances)
- 🔄 Real-time calendar updates using [BroadcastReceiver](https://developer.android.com/guide/components/broadcasts)
- 🔐 [Runtime permission](https://developer.android.com/training/permissions/requesting) handling for calendar access

## Project Structure

```
app/src/main/java/com/a4ary4n/calendarpoc/
├── MainActivity.kt              # Main entry point of the application
├── EventsActivity.kt           # Handles display of calendar events
├── InstancesActivity.kt        # Manages calendar event instances
├── CalendarReceiver.kt         # Broadcast receiver for calendar changes
├── adapters/                   # RecyclerView adapters
├── models/                     # Data models
├── utils/                      # Utility classes
├── viewmodels/                 # ViewModels for data management
└── views/                      # Custom view components
```

## Prerequisites

- [Android Studio](https://developer.android.com/studio) Arctic Fox or later
- Minimum SDK: 24 ([Android 7.0](https://developer.android.com/about/versions/nougat))
- Target SDK: 35
- Java Version: 11
- [Kotlin](https://kotlinlang.org/) with [ViewBinding](https://developer.android.com/topic/libraries/view-binding) enabled

## Setup

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Open the project in Android Studio

3. Build and run the application

## Permissions

The application requires the following permissions:

```xml
<uses-permission android:name="android.permission.READ_CALENDAR" />
```

The app implements [runtime permission handling](https://developer.android.com/training/permissions/requesting) for Android 6.0 (API level 23) and above.

## Key Components

### MainActivity
- Entry point of the application using [AppCompatActivity](https://developer.android.com/reference/androidx/appcompat/app/AppCompatActivity)
- Handles [calendar permission requests](https://developer.android.com/training/permissions/requesting)
- Displays list of available calendars using [RecyclerView](https://developer.android.com/develop/ui/views/layout/recyclerview)
- Manages navigation to Events and Instances views

### EventsActivity
- Displays events from selected calendars using [CalendarContract.Events](https://developer.android.com/reference/android/provider/CalendarContract.Events)
- Implements event filtering and viewing capabilities using [ContentResolver](https://developer.android.com/reference/android/content/ContentResolver)

### InstancesActivity
- Shows calendar event instances using [CalendarContract.Instances](https://developer.android.com/reference/android/provider/CalendarContract.Instances)
- Handles date-based event occurrence viewing

### CalendarReceiver
- [BroadcastReceiver](https://developer.android.com/guide/components/broadcasts) for calendar changes
- Ensures real-time updates when calendar content changes using [IntentFilter](https://developer.android.com/reference/android/content/IntentFilter)

## Architecture

The app follows the [MVVM (Model-View-ViewModel)](https://developer.android.com/topic/libraries/architecture/viewmodel) architecture pattern and uses the following Android Architecture Components:
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)

## Dependencies

- [AndroidX Core KTX](https://developer.android.com/kotlin/ktx)
- [AndroidX AppCompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
- [Material Design Components](https://material.io/develop/android)
- [AndroidX Activity](https://developer.android.com/jetpack/androidx/releases/activity)
- [AndroidX ConstraintLayout](https://developer.android.com/training/constraint-layout)
- [Gson](https://github.com/google/gson) 2.13.1 for JSON processing
- [JUnit](https://junit.org/junit4/) for testing
- [Espresso](https://developer.android.com/training/testing/espresso) for UI testing

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

[Add your license information here]

## Contact

[Add your contact information here]
