package com.example.flow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.database.db.MovesForTable
import com.example.flow.entities.Period
import com.example.flow.recyclerView.PeriodHistoryAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class Home : AppCompatActivity() {
    private lateinit var homeCalender: Button
    private lateinit var homeSelfCare: Button
    private lateinit var homeSetting: Button


    private lateinit var calendar: Calendar
    private lateinit var periodTextView: TextView
    private lateinit var homeOvulationTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private val PERIOD_AVERAGE_DATE = 30
    private val OVULATION_AVERAGE_DATE = 12

    private lateinit var movesForTable: MovesForTable
    lateinit var periodDate: MutableList<Period>
    private var userId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        movesForTable = MovesForTable(this)

        // get ID/email from shared preference
        val sharedPref = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE) ?: return
        userId = sharedPref.getInt("USER_ID", 0)

        periodDate = movesForTable.getPeriod(userId)

        periodTextView = findViewById(R.id.home_periodTextView)
        homeCalender = findViewById(R.id.home_calender)
        homeSelfCare = findViewById(R.id.home_selfCare)
        homeSetting = findViewById(R.id.home_setting)
        homeOvulationTextView = findViewById(R.id.home_ovulationTextView)
        recyclerView = findViewById(R.id.home_recyclerView)

        recyclerView.adapter = PeriodHistoryAdapter(periodDate)

        homeCalender.setOnClickListener { showDataRangePicker() }
        homeSelfCare.setOnClickListener {
            val intent = Intent(this, SelfCare::class.java)
            startActivity(intent)
        }
        homeSetting.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.page_setting -> {
            val mIntent = Intent(this, Setting::class.java)
            startActivity(mIntent)
            true
        }
        R.id.page_self_improvement -> {
            val mIntent = Intent(this, SelfCare::class.java)
            startActivity(mIntent)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun showDataRangePicker() {
        periodDate = movesForTable.getPeriod(userId)

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()

        dateRangePicker.show(
            supportFragmentManager,
            "date_range_picker"
        )

        // dateRangePicker set start date and end date in this

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            val startDate = dateSelected.first
            val endDate = dateSelected.second

            if (startDate != null && endDate != null) {
                movesForTable.insertPeriodData(
                    userId,
                    startDate,
                    endDate
                )
                val nextPeriod = nextPeriodDate(startDate)
                val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")

                val dateTime = simpleDateFormat.format(nextPeriod)
                periodTextView.setText(dateTime)

                val nextOvulation = ovulationDate(startDate)
                val nextOvulationDateTime = simpleDateFormat.format(nextOvulation)
                homeOvulationTextView.setText(nextOvulationDateTime)

            }
        }
    }

    private fun nextPeriodDate(time: Long): Date {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateString: String = simpleDateFormat.format(time)
        val date: Date = simpleDateFormat.parse(dateString)

        calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, PERIOD_AVERAGE_DATE)
        return calendar.time
    }

    private fun ovulationDate(time: Long): Date {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateString: String = simpleDateFormat.format(time)
        val date: Date = simpleDateFormat.parse(dateString)

        calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, OVULATION_AVERAGE_DATE)
        return calendar.time
    }
}