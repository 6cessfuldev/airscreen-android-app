package com.yuk.airscreen

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yuk.airscreen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(PassengerNotice(), "승객 예고")

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.passengerNotice -> replaceFragment(PassengerNotice(), "승객 예고")
                R.id.list -> replaceFragment(List(), "항공편 목록")
                R.id.search -> replaceFragment(Search(), "항공편 검색")
                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment, title : String) {

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val textView: TextView = toolbar.findViewById(R.id.toolbar_title)
        textView.text = title

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}