package com.example.overtone.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.overtone.R
import kotlinx.android.synthetic.main.item_view_pager.view.*

class ViewPagerAdapter(val chordDiagramIds: MutableList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
        return ViewPagerViewHolder(view)

    }

    override fun getItemCount(): Int {
        return chordDiagramIds.size
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewPagerViewHolder, position: Int) {
        val curImage = chordDiagramIds[position]
        holder.itemView.chordDiagramPic.setImageResource(curImage)

    }
}