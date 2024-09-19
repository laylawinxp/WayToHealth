package com.example.waytohealth.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applandeo.materialcalendarview.CalendarView
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.EventDay
import com.example.waytohealth.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.Calendar

class HomeFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private var events: MutableMap<String,String> = mutableMapOf()
    private lateinit var pieChart: PieChart


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
        calendarView = requireView().findViewById(R.id.calendarView)

        val calendars: ArrayList<CalendarDay> = ArrayList()
        val calendar = Calendar.getInstance()
        calendar.set(2024, 7,20)
        val calendarDay = CalendarDay(calendar)
        calendarDay.labelColor = R.color.pink
        calendarDay.imageResource = R.drawable.dumbbell
        calendars.add(calendarDay)
        events["10-09-2024"] = "Training"
        calendarView.setCalendarDays(calendars)

        pieChart = requireView().findViewById(R.id.pieChart)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(40f, "Category A"))
        entries.add(PieEntry(30f, "Category B"))
        entries.add(PieEntry(30f, "Category C"))

        val dataSet = PieDataSet(entries, "Example Chart")
        val data = PieData(dataSet)
        pieChart.data = data

        pieChart.invalidate()
    }



}