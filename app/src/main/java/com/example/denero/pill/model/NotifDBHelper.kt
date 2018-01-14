package com.example.denero.pill.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.denero.pill.NotificationAdapter
import com.example.denero.pill.PillAdapter

/**
 * Created by Denero on 12.01.2018.
 */
class NotifDBHelper(var context: Context):SQLiteOpenHelper(context,"myDataBaseNotification",null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table notification ("
                + "id integer primary key autoincrement,"
                + "timehh text,"
                + "timemm text,"
                + "name text,"
                + "period text,"
                + "dosa integer,"
                + "description text" +");")
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getArrayListNotif():ArrayList<Notif>{
        var arrayNotif = ArrayList<Notif>()
        var db = this.readableDatabase
        var c: Cursor = db.query("notification",null,null,null,null,null,null)
        c.moveToFirst()
        if( c != null && c.moveToFirst() ){
            Log.d("KOL_VO:",c.count.toString())
            val idColIndex = c.getColumnIndex("id")
            val titleColIndex = c.getColumnIndex("name")
            val descrColIndex = c.getColumnIndex("description")
            val timehhColIndex = c.getColumnIndex("timehh")
            val timemmColIndex = c.getColumnIndex("timemm")
            val periodColIndex = c.getColumnIndex("period")
            val dosaColIndex = c.getColumnIndex("dosa")
            var sPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            arrayNotif = ArrayList<Notif>()
            var i:Int = 0
            while (i<c.count){
                arrayNotif.add(Notif(
                        c.getInt(idColIndex),
                        c.getString(timehhColIndex),
                        c.getString(timemmColIndex),
                        c.getString(titleColIndex),
                        c.getString(periodColIndex),
                        c.getString(dosaColIndex),
                        c.getString(descrColIndex)
                ))
                c.moveToPosition(i+1)
                i++
            }
            c.close()
            db.close()
        }
        else
            arrayNotif = ArrayList<Notif>()

        return arrayNotif
    }

    fun ClearDB(){
        var db: SQLiteDatabase = this.writableDatabase
        db.delete("notification",null,null)
        Toast.makeText(context,"CLEAR COMPLETE",Toast.LENGTH_LONG).show()
    }

    fun AddNotifDB(timehh:String,timemm:String,name:String,period:String,dosa:Int,descr: String):Long{
        var cv = ContentValues()
        var db: SQLiteDatabase = this.writableDatabase
        cv.put("timehh",timehh)
        cv.put("timemm",timemm)
        cv.put("name",name)
        cv.put("description",descr)
        cv.put("period",period)
        cv.put("dosa",dosa)
        var id = db.insert("notification",null,cv)
        Log.d("NEW ID:",id.toString())
        db.close()
        return id
    }

    fun getNotifFromId(id:Int):Notif{
        var db = this.readableDatabase
        var result = Notif(0,"0000","000","note","0","0","Nothing")
        var c: Cursor = db.query("notification",null,null,null,null,null,null)
        c.moveToFirst()
        if( c != null){
            Log.d("KOL_VO:",c.count.toString())
            val idColIndex = c.getColumnIndex("id")
            val titleColIndex = c.getColumnIndex("name")
            val descrColIndex = c.getColumnIndex("description")
            val timehhColIndex = c.getColumnIndex("timehh")
            val timemmColIndex = c.getColumnIndex("timemm")
            val periodColIndex = c.getColumnIndex("period")
            val dosaColIndex = c.getColumnIndex("dosa")
            while (c.moveToNext()){
                if(c.getInt(idColIndex)==id){
                    result =  Notif(c.getInt(idColIndex),
                            c.getString(timehhColIndex),
                            c.getString(timemmColIndex),
                            c.getString(titleColIndex),
                            c.getString(periodColIndex),
                            c.getString(dosaColIndex),
                            c.getString(descrColIndex))
                    Log.d("RAZ<<RAZ",c.getInt(idColIndex).toString())
                }
            }

            c.close()
            db.close()
        }
        return result
    }

    fun DeleteTask(id:Int,list: RecyclerView){
        var cv = ContentValues()
        var db: SQLiteDatabase = this.writableDatabase
        db.delete("notification","id = "+id,null)
        db.close()
        NotifyData(list)
    }

    fun NotifyData(list:RecyclerView){
        var arrayNotif:ArrayList<Notif>
        var db = this.readableDatabase
        var c: Cursor = db.query("notification",null,null,null,null,null,null)
        c.moveToFirst()
        if( c != null && c.moveToFirst() ){
            Log.d("KOL_VO:",c.count.toString())
            val idColIndex = c.getColumnIndex("id")
            val titleColIndex = c.getColumnIndex("name")
            val descrColIndex = c.getColumnIndex("description")
            val timehhColIndex = c.getColumnIndex("timehh")
            val timemmColIndex = c.getColumnIndex("timemm")
            val periodColIndex = c.getColumnIndex("period")
            val dosaColIndex = c.getColumnIndex("dosa")
            var sPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            arrayNotif = ArrayList<Notif>()
            while (c.moveToNext()){
                arrayNotif.add(Notif(
                        c.getInt(idColIndex),
                        c.getString(timehhColIndex),
                        c.getString(timemmColIndex),
                        c.getString(titleColIndex),
                        c.getString(periodColIndex),
                        c.getString(dosaColIndex),
                        c.getString(descrColIndex)
                ))
            }
            c.close()
            db.close()
        }
        else
            arrayNotif = ArrayList<Notif>()

        list.adapter = NotificationAdapter(arrayNotif, list)
        list.adapter.notifyDataSetChanged()
    }

}
