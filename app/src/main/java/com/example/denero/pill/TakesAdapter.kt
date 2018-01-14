package com.example.denero.pill

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.denero.pill.model.Pill
import com.example.denero.pill.model.Take
import kotlinx.android.synthetic.main.takes_card.view.*


/**
 * Created by Denero on 15.01.2018.
 */
class TakesAdapter(var data:ArrayList<Pill>, var list: RecyclerView):RecyclerView.Adapter<TakesAdapter.TakesViewHolder>() {
    override fun onBindViewHolder(holder: TakesViewHolder?, position: Int) {
        holder!!.name.setText(data.get(position).name)
        holder!!.takesComplete.setText(data.get(position).takes.toString())
        holder!!.takesNotComplete.setText(data.get(position).nottakes.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TakesViewHolder = TakesAdapter.TakesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.takes_card, parent, false))

    override fun getItemCount(): Int = data.size

    class TakesViewHolder(var v:View):RecyclerView.ViewHolder(v) {
        var name:TextView = v.findViewById(R.id.tv_name)
        var takesComplete:TextView = v.findViewById(R.id.tv_takes_complete)
        var takesNotComplete:TextView = v.findViewById(R.id.tv_takes_not_complete)
    }
}