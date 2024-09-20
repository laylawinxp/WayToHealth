package com.example.waytohealth.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.applandeo.materialcalendarview.CalendarView
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.CalendarDay
import com.db.williamchart.view.DonutChartView
import com.example.waytohealth.R
import com.example.waytohealth.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.PieChart
import java.util.Calendar

class HomeFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private var events: MutableMap<String,String> = mutableMapOf()
    private lateinit var pieChart: PieChart

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ANIMATION_DURATION = 3000L

        private var donutSet = mutableListOf(
            20f,
            80f
        )

        private var barSet = mutableListOf(
            "мар" to 3F,
            "апр" to 4F,
            "май" to 6F,
            "июн" to 1F,
            "июл" to 3F,
            "авг" to 2F,
            "сен" to 5F,
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onResume() {
        super.onResume()
        val pinkColor = ContextCompat.getColor(requireContext(), R.color.pink)
        val greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)

        calendarView = requireView().findViewById(R.id.calendarView)

        calendarView.setHeaderColor(greenColor)

        val calendars: ArrayList<CalendarDay> = ArrayList()
        val calendar = Calendar.getInstance()
        calendar.set(2024, 7,20)
        val calendarDay = CalendarDay(calendar)
        calendarDay.labelColor = R.color.pink
        calendarDay.imageResource = R.drawable.dumbbell
        calendars.add(calendarDay)
        events["10-09-2024"] = "Training"
        calendarView.setCalendarDays(calendars)

        val donutChart = requireView().findViewById<DonutChartView>(R.id.donatChart)

        donutChart.donutColors = intArrayOf(
            pinkColor,
            whiteColor
        )
        donutChart.animation.duration = ANIMATION_DURATION
        donutChart.animate(donutSet)

        val percentOfTraining = kotlin.math.round(donutSet[0]).toInt()

        val donutText = requireView().findViewById<TextView>(R.id.donutText)
        donutText.text = "выполнили ${percentOfTraining}%"


        val barChart = requireView().findViewById<com.db.williamchart.view.BarChartView>(R.id.barChart)
        barChart.animation.duration = ANIMATION_DURATION
        barChart.animate(barSet)
    }



}