package com.example.denero.pill

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.denero.pill.model.DBHelper
import kotlinx.android.synthetic.main.create_pill.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Denero on 09.01.2018.
 */
class CreatePillActivity:AppCompatActivity() {
    lateinit var mOutputFileURI: Uri
    lateinit var temp:File
    lateinit var photoIV:ImageView
    lateinit var btnAdd:Button
    lateinit var edtName:EditText
    lateinit var edtDescr:EditText
    lateinit var edtInstr:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_pill)
        photoIV = pill_img
        btnAdd = btn_add_pill
        edtName = edt_name_pill
        edtDescr = edt_descr_pill
        edtInstr = edt_instr_pill

        photoIV.setOnClickListener {
            dispatchTakePictureIntent()
        }
        btnAdd.setOnClickListener {
            var dbHelper = DBHelper(applicationContext)
            dbHelper.AddPill(temp.absolutePath,edtName.text.toString(),edtDescr.text.toString(),edtInstr.text.toString())
            finish()
        }

    }

    fun dispatchTakePictureIntent()
    {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        temp = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath
                ,"hw_6"+ SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) +".jpg")
        mOutputFileURI = FileProvider.getUriForFile(applicationContext,
                applicationContext.getApplicationContext().getPackageName() + ".my.package.name.provider",
                temp)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,mOutputFileURI)
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(applicationContext,temp.absolutePath,Toast.LENGTH_LONG).show()
        photoIV.setImageBitmap(BitmapFactory.decodeFile(temp.absolutePath))
    }
}