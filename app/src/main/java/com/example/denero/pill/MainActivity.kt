package com.example.denero.pill

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import com.example.denero.pill.model.DBHelper
import com.example.denero.pill.model.NotifDBHelper
import com.example.denero.pill.model.Pill
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    lateinit var data:ArrayList<Pill>
    lateinit var dbHelper: DBHelper
    lateinit var btnCreatePill: FloatingActionButton
    lateinit var list:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = main_recview
        dbHelper = DBHelper(applicationContext)
        dbHelper.initData(list)
        btnCreatePill = fab
        btnCreatePill.setOnClickListener {

            val popup = PopupMenu(applicationContext, btnCreatePill)
            popup.inflate(R.menu.popup_menu_change)
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(p0: MenuItem?): Boolean {

                    when(p0?.order) {
                        99 ->{
                            var intent = Intent(applicationContext,CreatePillActivity::class.java)
                            startActivity(intent)
                        }
                        100 ->{
                            var intent = Intent(applicationContext,AlarmActivity::class.java)
                            startActivity(intent)
                        }
                        else->{
                            Toast.makeText(applicationContext, p0?.itemId.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                    return true
                }
            })
            popup.show()
            true

        }

    }


    override fun onResume() {
        super.onResume()
        dbHelper.NotifyData(list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu!!.add(Menu.NONE,1,Menu.NONE,"Show All Notification")
        menu!!.add(Menu.NONE,2,Menu.NONE,"Show All Takes")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId)
        {
            1 ->{
                var intent = Intent(applicationContext,NotificationActivity::class.java)
                startActivity(intent)
            }
            2 ->{
                var intent = Intent(applicationContext,TakesActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
