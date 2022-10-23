package com.example.flow.recyclerView

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flow.R
import com.example.flow.entities.DetailedArticles


class EmotionsAdapter(private val dataSet: MutableList<DetailedArticles> = mutableListOf()) :
    RecyclerView.Adapter<EmotionsAdapter.EmotionsViewHolder>() {
    private var context: Context? = null

    class EmotionsViewHolder(containerView: View) :
        RecyclerView.ViewHolder(containerView) {
        val albumImageView: ImageView = containerView.findViewById(R.id.album_ImageView)
        val cardTextView: TextView = containerView.findViewById(R.id.card_TextView)
        val cardLearnButton: Button = containerView.findViewById(R.id.card_learnButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.artical_card_view, parent, false)
        context = parent.context
        return EmotionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmotionsViewHolder, position: Int) {
        val currentdata = dataSet[position]
        holder.cardTextView.text = currentdata.title

        val image = currentdata.image.png400
        Log.e("url", currentdata.image.toString())

        if (image != null) {
            Log.e("url", "https://flo.health" + image)
            context?.let {
                Glide.with(it).load("https://flo.health" + image)
                    .into(holder.albumImageView)
            }
        }

        holder.cardLearnButton.setOnClickListener(View.OnClickListener {
            val website: String = currentdata.link
            Log.i("Location , ", website)
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://flo.health" + currentdata.link))
            context!!.startActivity(intent)
        })
    }

    override fun getItemCount() = dataSet.size
    fun addAlbumTitles(results: List<DetailedArticles>) {
        dataSet.addAll(results)
        notifyDataSetChanged()
    }
}

