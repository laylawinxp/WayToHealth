package com.example.waytohealth

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.waytohealth.fragments.HomeFragment
import com.example.waytohealth.fragments.ProfileFragment
import com.example.waytohealth.fragments.TrainingFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Главная", R.drawable.home)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Тренировки", R.drawable.dumbbell)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Профиль", R.drawable.user)
        )

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> {
                    replaceFragment(HomeFragment())
                }

                2 -> {
                    replaceFragment(TrainingFragment())
                }

                3 -> {
                    replaceFragment(ProfileFragment())
                }
            }
        }
        replaceFragment(TrainingFragment())
        bottomNavigation.show(2)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}