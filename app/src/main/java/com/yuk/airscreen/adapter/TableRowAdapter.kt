package com.yuk.airscreen.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuk.airscreen.R
import com.yuk.airscreen.model.DepartingFlightsInfo

class TableRowAdapter(
    private var infoArrayList: ArrayList<DepartingFlightsInfo>,
    private val onTableRowClick: (DepartingFlightsInfo) -> Unit) :
    RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {

    private var backgroundFlag: Boolean = true

    fun updateData(newData: List<DepartingFlightsInfo>) {
        infoArrayList = newData as ArrayList<DepartingFlightsInfo>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_row_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.departDateTv.text = infoArrayList[i].scheduleDateTime?.substring(8,12) ?: ""
        viewHolder.flightIdTv.text = infoArrayList[i].flightId
        viewHolder.airportTv.text = infoArrayList[i].airport
        viewHolder.checkIn.text = infoArrayList[i].chkinrange

        if(i != 0) {
            if(!(infoArrayList[i-1].scheduleDateTime == infoArrayList[i].scheduleDateTime
                || infoArrayList[i-1].gatenumber == infoArrayList[i].gatenumber)) {
                backgroundFlag = !backgroundFlag
            }
        }

        val backgroundColor: Int = if (backgroundFlag) {
            Color.WHITE
        } else {
            Color.parseColor("#ffe0e1dd")
        }

        viewHolder.itemView.setBackgroundColor(backgroundColor);

        viewHolder.itemView.setOnClickListener{
            onTableRowClick(infoArrayList[i])
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val departDateTv: TextView = itemView.findViewById(R.id.depart_date_tv)
        val flightIdTv: TextView = itemView.findViewById(R.id.flight_id_tv)
        val airportTv: TextView = itemView.findViewById(R.id.airport_tv)
        val checkIn: TextView = itemView.findViewById(R.id.checkin_tv)
    }

}
