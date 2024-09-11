package com.example.dnevnikvjezbanja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.dnevnikvjezbanja.HomeFragment
import com.example.dnevnikvjezbanja.AddExerciseFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Postavljanje početnog fragmenta
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment = HomeFragment()

            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_add -> selectedFragment = AddExerciseFragment()
                R.id.nav_settings -> selectedFragment = SettingsFragment()
            }

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()

            true
        }
    }

}
