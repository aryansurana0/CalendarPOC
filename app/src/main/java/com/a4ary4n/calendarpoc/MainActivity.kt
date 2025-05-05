package com.a4ary4n.calendarpoc

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
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
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.a4ary4n.calendarpoc.adapters.AdapterCalendars
import com.a4ary4n.calendarpoc.databinding.ActivityMainBinding
import com.a4ary4n.calendarpoc.utils.AppUtil.toast
import com.a4ary4n.calendarpoc.viewmodels.CalendarsViewModel
import com.google.gson.Gson

private const val TAG = "MainActivity: "

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CalendarsViewModel

    var prevToast: Toast? = null

    private val calendarReceiver = CalendarReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CalendarsViewModel::class.java]

        setClickListeners()

        binding.rvCalendars.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        registerReceiver()

    }

    private fun registerReceiver() {
        val filter = IntentFilter("android.intent.action.PROVIDER_CHANGED")
        val receiverFlag = ContextCompat.RECEIVER_EXPORTED
        ContextCompat.registerReceiver(this@MainActivity, calendarReceiver, filter, receiverFlag)
    }

    private fun setClickListeners() {
        binding.btnGetCalendars.setOnClickListener {
            checkPermissionOrRunQuery()
        }
        binding.btnEvents.setOnClickListener {
            openEvents(null)
        }
        binding.btnInstances.setOnClickListener {
            val intent = Intent(this, InstancesActivity::class.java)
            startActivity(intent)
        }
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
                this@MainActivity,
                Manifest.permission.READ_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED -> {
                println("part 1")
                runQuery()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CALENDAR
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

    private val openEvents: (calendarId: Long?) -> Unit = { calendarId ->
        println("click")
        val intent = Intent(this, EventsActivity::class.java)
        calendarId?.let { intent.putExtra("CALENDAR_ID", it) }
        startActivity(intent)
    }

    private fun runQuery() {
        val list = viewModel.getCalendars()
        binding.rvCalendars.adapter = AdapterCalendars(
            list,
            openEvents
        )
        println(Gson().toJson(list))
    }

    override fun onDestroy() {
        println("$TAG onDestroy")
        super.onDestroy()
        this.unregisterReceiver(calendarReceiver)
    }

}