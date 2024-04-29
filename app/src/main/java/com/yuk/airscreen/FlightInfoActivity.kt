package com.yuk.airscreen

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.yuk.airscreen.databinding.ActivityFlightInfoBinding
import com.yuk.airscreen.databinding.ActivityMainBinding
import com.yuk.airscreen.model.DepartingFlightsInfo

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
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // 뒤로가기 버튼 클릭 시 이전 액티비티로 이동하는 코드 작성
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}