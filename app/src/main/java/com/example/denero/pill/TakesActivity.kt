package com.example.denero.pill

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.denero.pill.model.DBHelper
import kotlinx.android.synthetic.main.takes_activity.*

/**
 * Created by Denero on 15.01.2018.
 */
class TakesActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.takes_activity)
        var list:RecyclerView = list_takes_rec_view
        var dbHelper = DBHelper(applicationContext)
        var data = dbHelper.getArrayListPill()
        list.layoutManager = LinearLayoutManager(applicationContext)
        list.adapter = TakesAdapter(data,list)
        list.adapter.notifyDataSetChanged()
    }
}