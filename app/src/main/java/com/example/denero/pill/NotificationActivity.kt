package com.example.denero.pill

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.denero.pill.model.NotifDBHelper
import kotlinx.android.synthetic.main.notification_list_activity.*

/**
 * Created by Denero on 14.01.2018.
 */
class NotificationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_list_activity)
        var notifDBHelper = NotifDBHelper(applicationContext)
        var data = notifDBHelper.getArrayListNotif()
        var list:RecyclerView = notification_rec_view
        list.layoutManager = LinearLayoutManager(applicationContext)
        list.adapter = NotificationAdapter(data,list)


    }
}