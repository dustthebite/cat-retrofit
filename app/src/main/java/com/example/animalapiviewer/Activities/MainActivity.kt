package com.example.animalapiviewer.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.animalapiviewer.Fragments.FavouritesFragment
import com.example.animalapiviewer.Fragments.HomeFragment
import com.example.animalapiviewer.Fragments.ProfileFragment
import com.example.animalapiviewer.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val favouritesFragment = FavouritesFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miMessages -> setCurrentFragment(favouritesFragment)
                R.id.miProfile -> setCurrentFragment(profileFragment)

            }
            true
        }



    }

    private fun setCurrentFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

}