package com.example.denero.pill

import android.app.AlarmManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.denero.pill.model.Notif
import com.example.denero.pill.model.NotifDBHelper
import com.example.denero.pill.model.Pill
import kotlinx.android.synthetic.main.notif_card.view.*

/**
 * Created by Denero on 14.01.2018.
 */
class NotificationAdapter(var data:ArrayList<Notif>, var list:RecyclerView): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    override fun onBindViewHolder(holder: NotificationViewHolder?, position: Int) {
        holder!!.tvName.setText(data.get(position).name)
        holder.tvDesct.setText((data.get(position).timehh
        +":"+ data.get(position).timemm))
        holder.btnDelete.setOnClickListener {
            var notifDBHelper = NotifDBHelper(holder.v.context)
            notifDBHelper.DeleteTask(data.get(position).id,list)
        }
    }

    override fun getItemCount(): Int = data.size



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NotificationViewHolder
            = NotificationViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.notif_card,parent,false))

    class NotificationViewHolder(var v: View):RecyclerView.ViewHolder(v) {
        var btnDelete:Button = v.item_btn_delete
        var tvName: TextView = v.item_name
        var tvDesct:TextView = v.item_description
    }

}