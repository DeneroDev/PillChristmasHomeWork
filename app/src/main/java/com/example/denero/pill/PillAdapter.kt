package com.example.denero.pill

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.denero.pill.model.DBHelper
import com.example.denero.pill.model.Pill
import kotlinx.android.synthetic.main.item_card.view.*

/**
 * Created by Denero on 08.01.2018.
 */
class PillAdapter(var data:ArrayList<Pill>, var list:RecyclerView):RecyclerView.Adapter<PillAdapter.PillViewHolder>() {
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PillViewHolder?, position: Int) {
        holder!!.photo.setImageBitmap(data.get(position).image)
        holder!!.nameTv.setText(data.get(position).name)
        holder!!.descrTv.setText(data.get(position).description)
        holder.istrB.setOnClickListener {
            showInstruction(holder.v.context,data.get(position).instruction)
        }

        holder.v.setOnLongClickListener {
            showPOPMenu(holder,position)
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PillViewHolder =
            PillViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_card,parent,false))

    class PillViewHolder(var v: View):RecyclerView.ViewHolder(v){
        var photo:ImageView = v.item_photo
        var nameTv:TextView = v.item_name
        var descrTv:TextView = v.item_description
        var istrB:Button = v.item_btn_intrc
    }

    fun showInstruction(context:Context,instruction:String){
        var dialog = AlertDialog.Builder(context)
        var constrLay = LayoutInflater.from(context).inflate(R.layout.instruction,null)
        dialog.setView(constrLay)
        var tv = constrLay.findViewById<TextView>(R.id.instruction)
        tv.setText(instruction)
        dialog.create()
        dialog.show()
    }

    fun showPOPMenu(holder:PillViewHolder?,position: Int){
        val popup = PopupMenu(holder!!.v.getContext(), holder.v)
        popup.inflate(R.menu.popup_menu)
        popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(p0: MenuItem?): Boolean {

                when(p0?.order) {
                    100 ->{
                        var dbHelper = DBHelper(holder.v.context)
                        dbHelper.DeleteTask(data.get(position).id,list)
                        dbHelper.NotifyData(list)
                    }
                    else->{
                        Toast.makeText(holder.v.context, p0?.itemId.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                return true
            }
        })
        popup.show()
        true
    }
    }


