package com.example.denero.pill.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.denero.pill.PillAdapter

/**
 * Created by Denero on 09.01.2018.
 */
class DBHelper(var context: Context): SQLiteOpenHelper(context,"DataBasePill",null,1) {


    override fun onCreate(p0: SQLiteDatabase?) {
        Log.d("SPAWN:", "--- onCreate database ---");
        p0?.execSQL("create table pill ("
                + "id integer primary key autoincrement,"
                + "image text,"
                + "name text,"
                + "nottakes integer,"
                + "takes integer,"
                + "description text,"
                + "instr text"+");")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun NotifyData(list:RecyclerView){
        var data:ArrayList<Pill>
        var option = BitmapFactory.Options()
        option.inSampleSize = 5
        var db = this.readableDatabase
        var c: Cursor = db.query("pill",null,null,null,null,null,null)
        c.moveToFirst()
        if( c != null && c.moveToFirst() ){
            Log.d("KOL_VO:",c.count.toString())
            val idColIndex = c.getColumnIndex("id")
            val imageColIndex = c.getColumnIndex("image")
            val titleColIndex = c.getColumnIndex("name")
            val descrColIndex = c.getColumnIndex("description")
            val instrColIndex = c.getColumnIndex("instr")
            val takesColIndex = c.getColumnIndex("takes")
            val nottakesColIndex = c.getColumnIndex("nottakes")
            var sPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            data = ArrayList<Pill>()
            var i:Int = 0
            while (i<c.count){
                data.add(Pill(
                        c.getInt(idColIndex),
                        BitmapFactory.decodeFile(c.getString(imageColIndex), option),
                        c.getString(titleColIndex),
                        c.getString(descrColIndex),
                        c.getString(instrColIndex),
                        c.getInt(takesColIndex),
                        c.getInt(nottakesColIndex)
                ))
                c.moveToPosition(i+1)
                i++
            }
            c.close()
            db.close()
        }
        else
            data = ArrayList<Pill>()

        list.adapter = PillAdapter(data, list)
        list.adapter.notifyDataSetChanged()
    }


  /*  fun getArray():Array<String>{
        var db = this.readableDatabase
        var c: Cursor = db.query("pill",null,null,null,null,null,null)
        c.moveToFirst()
        if( c != null && c.moveToFirst() ){
            var data: Array<String>
            val idColIndex = c.getColumnIndex("id")
        }

        return  data
    }*/

    fun getArrayListPill():ArrayList<Pill>{
        var data:ArrayList<Pill>
        var option = BitmapFactory.Options()
        option.inSampleSize = 5
        var db = this.readableDatabase
        var c: Cursor = db.query("pill",null,null,null,null,null,null)
        c.moveToFirst()
        if( c != null && c.moveToFirst() ){
            Log.d("KOL_VO:",c.count.toString())
            val idColIndex = c.getColumnIndex("id")
            val imageColIndex = c.getColumnIndex("image")
            val titleColIndex = c.getColumnIndex("name")
            val descrColIndex = c.getColumnIndex("description")
            val instrColIndex = c.getColumnIndex("instr")
            val takesColIndex = c.getColumnIndex("takes")
            val nottakesColIndex = c.getColumnIndex("nottakes")
            var sPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            data = ArrayList<Pill>()
            var i:Int = 0
            while (i<c.count){
                data.add(Pill(
                        c.getInt(idColIndex),
                        BitmapFactory.decodeFile(c.getString(imageColIndex), option),
                        c.getString(titleColIndex),
                        c.getString(descrColIndex),
                        c.getString(instrColIndex),
                        c.getInt(takesColIndex),
                        c.getInt(nottakesColIndex)
                ))
                c.moveToPosition(i+1)
                i++
            }
            c.close()
            db.close()
        }
        else
            data = ArrayList<Pill>()

        return data
    }


    fun Takes(add:Boolean,id:Int,takes:Int,nottakes:Int){
        var cv = ContentValues()
        var db: SQLiteDatabase = this.writableDatabase
        if(add){
            cv.put("takes",takes+1)
        }
        else
        {
            cv.put("nottakes",nottakes+1)
        }

        var id = db.update("pill", cv, "id = "+id,null)
        Log.d("NEW ID:",id.toString())
        db.close()
    }


    fun ClearDB(){
        var db: SQLiteDatabase = this.writableDatabase
        db.delete("pill",null,null)
    }


    fun AddPill(image:String,name:String,descr:String,instr:String){
        var cv = ContentValues()
        var db: SQLiteDatabase = this.writableDatabase
        cv.put("image",image)
        cv.put("name",name)
        cv.put("description",descr)
        cv.put("instr",instr)
        var id = db.insert("pill",null,cv)
        Log.d("NEW ID:",id.toString())
        db.close()
    }

    fun UpdateTask(id:Int, title: String, descr: String, data:ArrayList<Pill>, list: RecyclerView){
        var cv = ContentValues()
        var db: SQLiteDatabase = this.writableDatabase
        cv.put("title", title)
        cv.put("description", descr)
        // обновляем по id
        db.update("mytable", cv, "id = "+id,null)
        db.close()
       // NotifyData(data,list)
    }

    fun DeleteTask(id:Int,list: RecyclerView){
        var cv = ContentValues()
        var db: SQLiteDatabase = this.writableDatabase
        db.delete("pill","id = "+id,null)
        db.close()
        NotifyData(list)
    }

    fun initData(list: RecyclerView) {
        var data:ArrayList<Pill>
        var option = BitmapFactory.Options()
        option.inSampleSize = 5
        var db = this.readableDatabase
        var c: Cursor = db.query("pill",null,null,null,null,null,null)
        c.moveToFirst()
        if( c != null && c.moveToFirst() ){
            Log.d("KOL_VO:",c.count.toString())
            val idColIndex = c.getColumnIndex("id")
            val imageColIndex = c.getColumnIndex("image")
            val titleColIndex = c.getColumnIndex("name")
            val descrColIndex = c.getColumnIndex("description")
            val instrColIndex = c.getColumnIndex("instr")
            val takesColIndex = c.getColumnIndex("takes")
            val nottakesColIndex = c.getColumnIndex("nottakes")
            var sPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            data = ArrayList<Pill>()
            var i:Int = 0
            while (i<c.count){
                    data.add(Pill(
                            c.getInt(idColIndex),
                            BitmapFactory.decodeFile(c.getString(imageColIndex), option),
                            c.getString(titleColIndex),
                            c.getString(descrColIndex),
                            c.getString(instrColIndex),
                            c.getInt(takesColIndex),
                            c.getInt(nottakesColIndex)
                    ))
                    c.moveToPosition(i+1)
                    i++
            }
            c.close()
            db.close()
        }
        else
            data = ArrayList<Pill>()

       list.layoutManager = LinearLayoutManager(context)
       list.adapter = PillAdapter(data, list)
       list.adapter.notifyDataSetChanged()


    }

    fun checkedTask(id:Int, check:Int, data:ArrayList<Pill>, list: RecyclerView){
        var cv = ContentValues()
        var db: SQLiteDatabase = this.writableDatabase
        cv.put("complete",check)
        // обновляем по id
        db.update("mytable", cv, "id = "+id,null)
        db.close()
        //NotifyData(data,list)
    }
}