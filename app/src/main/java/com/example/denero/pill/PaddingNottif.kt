package com.example.denero.pill

import android.app.AlarmManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.denero.pill.model.AlarmManagerBroadcastReciver
import com.example.denero.pill.model.DBHelper
import com.example.denero.pill.model.Pill
import kotlinx.android.synthetic.main.padding_nottif.*

/**
 * Created by Denero on 14.01.2018.
 */
class PaddingNottif:AppCompatActivity() {
    lateinit var btnOk:Button
    lateinit var btnPast:Button
    lateinit var btnCancel:Button
    lateinit var pill:Pill
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.padding_nottif)
        var id = intent.extras.getLong("id").toInt()
        var idNott = intent.extras.getLong("idNottif")
        var dbH = DBHelper(applicationContext)
        btnOk = btn_ok
        btnCancel = btn_cancel
        btnPast = btn_late
        var data = dbH.getArrayListPill()
        for(pillT in data){
            if(pillT.id == id)
                pill = pillT
        }

        btnOk.setOnClickListener {
            dbH.Takes(true,id,pill.takes,pill.nottakes)
            finish()
        }
        btnCancel.setOnClickListener{
                    dbH.Takes(false,id,pill.takes,pill.nottakes)
            finish()
        }
        btnPast.setOnClickListener {
            var alarm = AlarmManagerBroadcastReciver()
            alarm.setOnetimeTimer(applicationContext,
                    System.currentTimeMillis()+AlarmManager.INTERVAL_FIFTEEN_MINUTES+AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    idNott,id.toLong())
            finish()
        }

    }
}