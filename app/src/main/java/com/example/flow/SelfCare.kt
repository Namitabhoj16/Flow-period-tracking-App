package com.example.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.flow.recyclerView.PeriodAdapter
import com.example.flow.network.FlowApi
import com.example.flow.entities.Articles
import com.example.flow.recyclerView.DischargeAdapter
import com.example.flow.recyclerView.EmotionsAdapter
import com.example.flow.recyclerView.OvulationAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelfCare : AppCompatActivity() {
    private val periodAdapter = PeriodAdapter()
    private val ovulationAdapter = OvulationAdapter()
    private val dischargeAdapter = DischargeAdapter()
    private val emotionAdapter = EmotionsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_care)

        val callPeriod = FlowApi.retrofitService.periodResults(
            "1",
            "json",
            "9"
        )
        callPeriod.enqueue(object : Callback<Articles> {
            override fun onResponse(
                call: Call<Articles>,
                response: Response<Articles>
            ) {
                val articals = response.body() as Articles
                Log.e("", articals.toString())
                periodAdapter.addAlbumTitles(articals.items)
            }

            override fun onFailure(call: Call<Articles>, t: Throwable) {
                Log.e("message", "error")
            }

        })

        val callDischarge = FlowApi.retrofitService.dischargeResults(
            "1",
            "json",
            "9"
        )
        callDischarge.enqueue(object : Callback<Articles> {
            override fun onResponse(
                call: Call<Articles>,
                response: Response<Articles>
            ) {
                val articles = response.body() as Articles
                Log.e("", articles.toString())
                dischargeAdapter.addAlbumTitles(articles.items)
            }

            override fun onFailure(call: Call<Articles>, t: Throwable) {
                Log.e("message", "error")
            }

        })

        val callOvulation = FlowApi.retrofitService.ovulationResults(
            "1",
            "json",
            "9"
        )
        callOvulation.enqueue(object : Callback<Articles> {
            override fun onResponse(
                call: Call<Articles>,
                response: Response<Articles>
            ) {
                val articals = response.body() as Articles
                Log.e("", articals.toString())
                ovulationAdapter.addAlbumTitles(articals.items)
            }

            override fun onFailure(call: Call<Articles>, t: Throwable) {
                Log.e("message", "error")
            }

        })

        val callEmotion = FlowApi.retrofitService.emotionsResults(
            "1",
            "json",
            "9"
        )
        callEmotion.enqueue(object : Callback<Articles> {
            override fun onResponse(
                call: Call<Articles>,
                response: Response<Articles>
            ) {
                val articals = response.body() as Articles
                Log.e("", articals.toString())
                emotionAdapter.addAlbumTitles(articals.items)
            }

            override fun onFailure(call: Call<Articles>, t: Throwable) {
                Log.e("message", "error")
            }

        })

        val periodRecyclerView = findViewById<RecyclerView>(R.id.main_periodRecyclerView)
        val ovulationRecyclerView = findViewById<RecyclerView>(R.id.main_ovulationRecyclerView)
        val dischargeRecyclerView = findViewById<RecyclerView>(R.id.main_dischargeRecyclerView)
        val emotionRecyclerView = findViewById<RecyclerView>(R.id.main_emotionsRecyclerView)

        periodRecyclerView.adapter = periodAdapter
        ovulationRecyclerView.adapter = ovulationAdapter
        dischargeRecyclerView.adapter = dischargeAdapter
        emotionRecyclerView.adapter = emotionAdapter
    }
}