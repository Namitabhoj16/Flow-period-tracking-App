package com.example.flow.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flow.R
import com.example.flow.entities.Period
import java.text.SimpleDateFormat
import java.util.*

class PeriodHistoryAdapter(private val dataSet: MutableList<Period>) :
    RecyclerView.Adapter<PeriodHistoryAdapter.HistoryItemViewHolder>() {
    class HistoryItemViewHolder(private val containerView: View) :
        RecyclerView.ViewHolder(containerView) {
        val start_date: TextView = containerView.findViewById(R.id.list_start_date_textview)
        val end_date: TextView = containerView.findViewById(R.id.list_end_date_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, customViewType: Int): HistoryItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.period_history,
                parent,
                false
            ) //false because the recycler will add to the view hierarchy when it is time
        return HistoryItemViewHolder(view)
    }

    //Called by the layoutManager to replace the content(data) of the CustomView
    override fun onBindViewHolder(holder: HistoryItemViewHolder, positionInDataSet: Int) {

        val currentData = dataSet[positionInDataSet]

//        val pattern = "dd-MM-yy"
//        val simpleDateFormat = SimpleDateFormat(pattern)
//        val startDate: String = simpleDateFormat.format(currentData.start_date)
//        val date: Date = simpleDateFormat.parse(startDate)
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.time = date
//        calendar.add(Calendar.DATE, 1)
//        val dateTime = simpleDateFormat.format(calendar.time)


        //val endDate: String = simpleDateFormat.format(currentData.end_date)

        holder.start_date.setText(addDay(currentData.start_date))
        holder.end_date.setText(addDay(currentData.end_date))

    }

    fun addDay(date: Long): String {
        val pattern = "dd-MM-yy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val startDate: String = simpleDateFormat.format(date)
        val date: Date = simpleDateFormat.parse(startDate)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, 1)
        val dateTime = simpleDateFormat.format(calendar.time)
        return dateTime
    }

    override fun getItemCount() = dataSet.size
}