package com.yuk.airscreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.yuk.airscreen.databinding.ActivityFlightInfoBinding
import com.yuk.airscreen.databinding.ActivityMainBinding
import com.yuk.airscreen.model.DepartingFlightsInfo
import com.yuk.airscreen.service.dataservice.DataService

class FlightInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFlightInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        val model: DepartingFlightsInfo?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            model = intent.getParcelableExtra("flightInfo",DepartingFlightsInfo::class.java)
        } else {
            model = @Suppress("DEPRECATION") intent.getParcelableExtra("flightInfo") as? DepartingFlightsInfo
        }

        if(model != null) {
            with(binding) {
                infoAirlineTv.text = model.airline
                infoFlightIdTv.text = model.flightId
                infoAirportTv.text = model.airport
                infoCheckCounterTv.text = model.chkinrange
                infoGateNumberTv.text = model.gatenumber
                infoScheduleTimeTv.text = model.scheduleDateTime
                infoTerminalIdTv.text = model.terminalid

                airlineCallTv.setOnClickListener{
                    val airline = model.airline
                    val phoneNumber = DataService.airlineCallNum[airline]

                    if (!phoneNumber.isNullOrEmpty()) {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$phoneNumber")
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}