package com.example.denero.pill.model

import android.graphics.Bitmap

/**
 * Created by Denero on 08.01.2018.
 */
open class Pill(var id:Int,var image:Bitmap, var name:String, var description:String, var instruction:String,var takes:Int,var nottakes:Int) {
}