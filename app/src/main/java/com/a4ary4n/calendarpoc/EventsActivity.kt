package com.a4ary4n.calendarpoc

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.a4ary4n.calendarpoc.adapters.AdapterEvents
import com.a4ary4n.calendarpoc.databinding.ActivityEventsBinding
import com.a4ary4n.calendarpoc.utils.AppUtil.toast
import com.a4ary4n.calendarpoc.viewmodels.CalendarsViewModel
import com.google.gson.Gson
import kotlin.jvm.java

private const val TAG = "EventsActivity: "

class EventsActivity : AppCompatActivity() {

    var prevToast: Toast? = null

    private lateinit var binding: ActivityEventsBinding
    private var calendarId: Long? = null
    private lateinit var calendarsViewModel: CalendarsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (intent.extras?.containsKey("CALENDAR_ID") == true) {
            calendarId = intent.extras?.getLong("CALENDAR_ID")
            toast("$TAG $calendarId")
        }
        calendarsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CalendarsViewModel::class.java]

        binding.tvId.text = calendarId.toString()
        binding.rvEvents.apply {
            layoutManager =
                LinearLayoutManager(this@EventsActivity, LinearLayoutManager.VERTICAL, false)
        }

        checkPermissionOrRunQuery()
    }


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                runQuery()
            } else {
                prevToast = toast("$TAG why you salty?", prevToast)
                println("part 2")
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", packageName, null)
                startActivity(intent)
//                requestPermissionLauncher.launch(
//                    Manifest.permission.READ_CALENDAR
//                )
            }
        }

    private fun checkPermissionOrRunQuery() {
        when {
            ContextCompat.checkSelfPermission(
                this@EventsActivity,
                android.Manifest.permission.READ_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED -> {
                println("part 1")
                runQuery()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.READ_CALENDAR
            ) -> {
                println("part 2")
                prevToast = toast("$TAG give the permission", prevToast)
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", packageName, null)
                startActivity(intent)
//                requestPermissionLauncher.launch(
//                    Manifest.permission.READ_CALENDAR
//                )
            }

            else -> {
                println("part 3")
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CALENDAR
                )
            }
        }
    }

    private val openInstances: (eventId: Long?) -> Unit = { eventId ->
        println("click")
        val intent = Intent(this, InstancesActivity::class.java)
        eventId?.let { intent.putExtra("EVENT_ID", it) }
        startActivity(intent)
    }


    private fun runQuery() {
        val list = calendarsViewModel.getEvents(calendarId)
        binding.rvEvents.adapter = AdapterEvents(list, openInstances)
        println(Gson().toJson(list))
    }


}