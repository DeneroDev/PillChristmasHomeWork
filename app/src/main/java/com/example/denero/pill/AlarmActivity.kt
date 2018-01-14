package com.example.denero.pill

import android.app.AlarmManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.denero.pill.model.*
import kotlinx.android.synthetic.main.alarm_activity.*


/**
 * Created by Denero on 09.01.2018.
 */
class AlarmActivity():AppCompatActivity() {

    private lateinit var alarm:AlarmManagerBroadcastReciver
    lateinit var spinner:Spinner
    lateinit var dbHelper:DBHelper
    lateinit var notifDbHelper:NotifDBHelper
    lateinit var dataPill:ArrayList<Pill>
    lateinit var notifPill:ArrayList<Notif>
    var chooseId = 0L
    var chooseIdPill = 0L
    var choose = 0
    lateinit var edtPeriod:EditText
    lateinit var edtTimeHH:EditText
    lateinit var edtTimeMM:EditText
    lateinit var edtDosa:EditText
    lateinit var radioGroup:RadioGroup
    var pastfood = false
    var nextfood = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_activity)
        edtPeriod = edt_period
        edtTimeHH = edt_time_hh
        edtTimeMM = edt_time_mm
        edtDosa = edt_dosa
        radioGroup = radiogroup
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb_nextfood -> nextfood = true
                R.id.rb_pastfood -> pastfood = true
            }
        }

        spinner = findViewById(R.id.spinner)
        dbHelper = DBHelper(applicationContext)
        notifDbHelper = NotifDBHelper(applicationContext)
        dataPill = dbHelper.getArrayListPill()
        notifPill = notifDbHelper.getArrayListNotif()
        var namePill = ArrayList<String>()
        for(pill in dataPill){
            namePill.add(pill.name)
        }
        var adapter = ArrayAdapter<kotlin.String>(applicationContext,android.R.layout.simple_spinner_item,namePill)
        spinner.adapter = adapter

        var btnAlarm:Button = btn_alarm
        btnAlarm.setOnClickListener {
            val context = applicationContext
                var period = edtPeriod.text.toString().toLong()

                var dosa  = edtDosa.text.toString().toInt()
                Log.d("edtTimeHH",edtTimeHH.text.toString())
                 Log.d("edtTimeMM",edtTimeMM.text.toString())
                chooseId = notifDbHelper.AddNotifDB(edtTimeHH.text.toString(),edtTimeMM.text.toString(),
                        dataPill.get(choose).name,period.toString(),dosa,dataPill.get(choose).description)

                alarm = AlarmManagerBroadcastReciver()
                alarm.SetAlarm(context,chooseId,chooseIdPill,nextfood,pastfood)
                Toast.makeText(applicationContext,"Напоминание добавлено",Toast.LENGTH_LONG).show()
          /*  }catch (e:Exception){
                Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show()
            }*/
            finish()
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                choose = p2
                chooseIdPill = dataPill.get(p2).id.toLong()
                chooseId = dataPill.get(p2).id.toLong()
            }
        }
    }

}