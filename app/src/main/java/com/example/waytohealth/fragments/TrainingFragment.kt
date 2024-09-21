package com.example.waytohealth.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.waytohealth.DBHelper
import com.example.waytohealth.R
import java.util.Calendar

class TrainingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_training, container, false)
    }

    override fun onResume() {
        super.onResume()

        val balancingIB = requireView().findViewById<ImageButton>(R.id.balancing_ib)
        val enduranceIB = requireView().findViewById<ImageButton>(R.id.endurance_ib)
        val powerIB = requireView().findViewById<ImageButton>(R.id.power_ib)
        val easyIB = requireView().findViewById<ImageButton>(R.id.easy_ib)
        val normIB = requireView().findViewById<ImageButton>(R.id.norm_ib)
        val hardIB = requireView().findViewById<ImageButton>(R.id.hard_ib)

        val balancingTXT = requireView().findViewById<TextView>(R.id.balancing_txt)
        val enduranceTXT = requireView().findViewById<TextView>(R.id.endurance_txt)
        val powerTXT = requireView().findViewById<TextView>(R.id.power_txt)
        val easyTXT = requireView().findViewById<TextView>(R.id.easy_txt)
        val normTXT = requireView().findViewById<TextView>(R.id.norm_txt)
        val hardTXT = requireView().findViewById<TextView>(R.id.hard_txt)

        var type = "balancing"
        var level = "easy"

        balancingIB.setOnClickListener {
            balancingIB.setImageResource(R.drawable.balancing_selected)
            enduranceIB.setImageResource(R.drawable.endurance)
            powerIB.setImageResource(R.drawable.power)

            balancingTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            enduranceTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            powerTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            type = "balancing"
        }

        enduranceIB.setOnClickListener {
            balancingIB.setImageResource(R.drawable.balancing)
            enduranceIB.setImageResource(R.drawable.endurance_selected)
            powerIB.setImageResource(R.drawable.power)

            balancingTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            enduranceTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            powerTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            type = "endurance"
        }

        powerIB.setOnClickListener {
            balancingIB.setImageResource(R.drawable.balancing)
            enduranceIB.setImageResource(R.drawable.endurance)
            powerIB.setImageResource(R.drawable.power_selected)

            balancingTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            enduranceTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            powerTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

            type = "power"
        }

        easyIB.setOnClickListener {
            easyIB.setImageResource(R.drawable.easy_selected)
            normIB.setImageResource(R.drawable.norm)
            hardIB.setImageResource(R.drawable.hard)

            easyTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            normTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            hardTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            level = "easy"
        }

        normIB.setOnClickListener {
            easyIB.setImageResource(R.drawable.easy)
            normIB.setImageResource(R.drawable.norm_selected)
            hardIB.setImageResource(R.drawable.hard)

            easyTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            normTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            hardTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            level = "norm"
        }

        hardIB.setOnClickListener {
            easyIB.setImageResource(R.drawable.easy)
            normIB.setImageResource(R.drawable.norm)
            hardIB.setImageResource(R.drawable.hard_selected)

            easyTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            normTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            hardTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

            level = "hard"
        }

        val buttStart: Button = requireView().findViewById(R.id.start)

        buttStart.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.training_layout, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.show()

            val calendar = Calendar.getInstance()
            val db = DBHelper(requireContext(), null)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH) + 1
            val year = calendar.get(Calendar.YEAR)

            if(!(db.checkDataForDay(day, month, year))){
                db.addDataForDay(day, month, year)
            }

            val buttEnd: Button = dialogBinding.findViewById(R.id.end)
            buttEnd.setOnClickListener {
                db.changeTrainingsValue(day, month, year)
                myDialog.dismiss()
            }
        }


    }

}