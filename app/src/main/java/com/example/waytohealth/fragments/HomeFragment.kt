package com.example.waytohealth.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.applandeo.materialcalendarview.CalendarDay
import com.example.waytohealth.R
import java.util.Calendar

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val calendarDays: MutableList<CalendarDay> = ArrayList<CalendarDay>()

        val calendar = Calendar.getInstance()
        val calendarDay = CalendarDay(calendar)
        calendarDay.imageResource = R.drawable.dumbbell
        calendarDays.add(calendarDay)


//or if you want to specify event label color
        //calendarDay.labelColor = Color.parseColor("#228B22")
        //calendarDays.add(calendarDay)

            // val calendarView: CalendarView = requireView().findViewById(R.id.calendarView)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }



}