package com.example.waytohealth.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.applandeo.materialcalendarview.CalendarView
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.CalendarDay
import com.db.williamchart.view.DonutChartView
import com.example.waytohealth.DBHelper
import com.example.waytohealth.R
import com.example.waytohealth.databinding.FragmentHomeBinding
import java.util.Calendar
import kotlin.math.abs

class HomeFragment : Fragment() {

    private lateinit var calendarView: CalendarView

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val monthsName = listOf(
        "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"
    )

    companion object {
        private const val ANIMATION_DURATION = 3000L

        private var donutSet = mutableListOf(
            0f,
            100f
        )

        private var barSet = mutableListOf(
            "" to 0F,
            "" to 0F,
            "" to 0F,
            "" to 0F,
            "" to 0F,
            "" to 0F
        )

        val goal = 20
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
        val donutChart = requireView().findViewById<DonutChartView>(R.id.donatChart)

        donutChart.donutColors = intArrayOf(
            pinkColor,
            whiteColor
        )
        donutChart.animation.duration = ANIMATION_DURATION

        val calendar = Calendar.getInstance()

        val db = DBHelper(requireContext(), null)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        if (!(db.checkDataForDay(day, month, year))) {
            db.addDataForDay(day, month, year)
        }

        if (!(db.checkDataForMonth(month))) {
            db.addDataForMonth(month)
        }

        val allDates = db.getAllDates()
        db.displayAllData()
        val calendars: ArrayList<CalendarDay> = ArrayList()

        for (el in allDates) {
            val day_ = el.first
            val month_ = el.second
            val year_ = el.third
            val currTrainings = db.getCurrentTrainingsOfDay(day_, month_, year_)
            if (currTrainings > 0) {
                val calendar = Calendar.getInstance()
                calendar.set(year_, month_ - 1, day_)
                val calendarDay = CalendarDay(calendar)
                calendarDay.labelColor = R.color.pink
                calendarDay.imageResource = R.drawable.dumbbell
                calendars.add(calendarDay)
            }
        }
        calendarView.setCalendarDays(calendars)
        if (!(barSet.any { it.first == monthsName[month - 1] })) {
            for (i in 0..5) {
                val monthIndex = (month - 1 - i + 12) % 12
                val monthName = monthsName[monthIndex]
                if (db.checkDataForMonth(monthIndex + 1)) {
                    barSet[5 - i] =
                        monthName to db.getCurrentTrainingsOfMonth(monthIndex + 1).toFloat()
                } else {
                    barSet[5 - i] = monthName to 0.1F
                }
            }
        }

        val barChart =
            requireView().findViewById<com.db.williamchart.view.BarChartView>(R.id.barChart)
        barChart.animation.duration = ANIMATION_DURATION
        barChart.animate(barSet)

        val percent = 100F * db.getCurrentTrainingsOfMonth(month).toFloat() / goal

        val percentOfTraining = kotlin.math.round(percent).toInt()
        donutSet[0] = percent
        donutSet[1] = 100 - percent
        donutChart.animate(donutSet)

        val donutText = requireView().findViewById<TextView>(R.id.donutText)
        donutText.text = "выполнили ${percentOfTraining}%"
    }

}