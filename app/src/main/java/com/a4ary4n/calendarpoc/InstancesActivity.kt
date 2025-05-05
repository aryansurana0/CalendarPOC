package com.a4ary4n.calendarpoc

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.a4ary4n.calendarpoc.adapters.AdapterInstances
import com.a4ary4n.calendarpoc.databinding.ActivityInstancesBinding
import com.a4ary4n.calendarpoc.utils.AppUtil.toast
import com.a4ary4n.calendarpoc.utils.AppUtil.y4_M2_d2_hh_mm_ss
import com.a4ary4n.calendarpoc.viewmodels.CalendarsViewModel
import com.google.gson.Gson
import java.util.Calendar

private const val TAG = "InstancesActivity: "

class InstancesActivity : AppCompatActivity() {

    private var prevToast: Toast? = null

    private lateinit var binding: ActivityInstancesBinding
    private var eventId: Long? = null
    private lateinit var viewModel: CalendarsViewModel

    private var startTime: Calendar = Calendar.getInstance()
    private var endTime: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstancesBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (intent.extras?.containsKey("EVENT_ID") == true) {
            eventId = intent.extras?.getLong("EVENT_ID")
            toast("$TAG $eventId")
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CalendarsViewModel::class.java]

        initListeners()

        binding.tvEventId.text = eventId.toString()
        binding.rvInstances.apply {
            layoutManager =
                LinearLayoutManager(this@InstancesActivity, LinearLayoutManager.VERTICAL, false)
        }
        updateDateTextViews()
    }

    private fun initListeners() {
        binding.btnStartTime.setOnClickListener {
            println("before start: ${y4_M2_d2_hh_mm_ss.format(startTime.time)}")
            val startYear = startTime.get(Calendar.YEAR)
            val startMonth = startTime.get(Calendar.MONTH)
            val startDay = startTime.get(Calendar.DAY_OF_MONTH)
            val startHour = startTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = startTime.get(Calendar.MINUTE)
            showDateTimePickerDialog(
                year = startYear,
                month = startMonth,
                dayOfMonth = startDay,
                hour = startHour,
                minute = startMinute,
                maxCal = endTime
            ) {
                startTime.timeInMillis = it
                updateDateTextViews()
                println("after start: ${y4_M2_d2_hh_mm_ss.format(startTime.time)}")
            }
        }

        binding.btnEndTime.setOnClickListener {
            println("before end: ${y4_M2_d2_hh_mm_ss.format(endTime.time)}")
            val endYear = endTime.get(Calendar.YEAR)
            val endMonth = endTime.get(Calendar.MONTH)
            val endDay = endTime.get(Calendar.DAY_OF_MONTH)
            val endHour = endTime.get(Calendar.HOUR_OF_DAY)
            val endMinute = endTime.get(Calendar.MINUTE)
            showDateTimePickerDialog(
                year = endYear,
                month = endMonth,
                dayOfMonth = endDay,
                hour = endHour,
                minute = endMinute,
                minCal = startTime
            ) {
                endTime.timeInMillis = it
                updateDateTextViews()
                println("after end: ${y4_M2_d2_hh_mm_ss.format(endTime.time)}")
            }
        }

        binding.btnGetInstances.setOnClickListener {
            checkPermissionOrRunQuery()
//            val list = viewModel.getInstances(
//                eventId = null,
//                startMillis = startTime.timeInMillis,
//                endMillis = endTime.timeInMillis
//            )
        }
    }

    private fun showDateTimePickerDialog(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hour: Int,
        minute: Int,
        minCal: Calendar? = null,
        maxCal: Calendar? = null,
        setTime: (Long) -> Unit
    ) {
        val datePickerDialog = DatePickerDialog(
            this@InstancesActivity,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                TimePickerDialog(
                    this@InstancesActivity,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val cal = Calendar.getInstance().apply {
                            set(year, month, day, hourOfDay, minute)
                        }
                        setTime(cal.timeInMillis)
                    },
                    hour,
                    minute,
                    true,
                ).show()
            },
            year,
            month,
            dayOfMonth
        )
        minCal?.let {
            datePickerDialog.datePicker.minDate = it.timeInMillis
        }
        maxCal?.let {
            datePickerDialog.datePicker.maxDate = it.timeInMillis
        }

        datePickerDialog.setCancelable(false)
        datePickerDialog.setCanceledOnTouchOutside(false)

        datePickerDialog.show()
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
                this@InstancesActivity,
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


    private fun runQuery() {
        val list = viewModel.getInstances(
            eventId = eventId,
            startMillis = startTime.timeInMillis,
            endMillis = endTime.timeInMillis
        )
        binding.rvInstances.adapter = AdapterInstances(
            list,
        )
        println(Gson().toJson(list))
    }

    private fun updateDateTextViews() {
        binding.tvStartTime.text = y4_M2_d2_hh_mm_ss.format(startTime.time)
        binding.tvEndTime.text = y4_M2_d2_hh_mm_ss.format(endTime.time)
    }

}
