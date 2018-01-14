package com.example.denero.pill.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.widget.Toast
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import com.example.denero.pill.MainActivity
import android.app.NotificationManager
import com.example.denero.pill.R
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.denero.pill.PaddingNottif
import java.sql.Time


/**
 * Created by Denero on 09.01.2018.
 */
class AlarmManagerBroadcastReciver():BroadcastReceiver() {
    var id = 0
    var pill = "Not"
    var period = 0L
    var time:Long = 10L
    var pastfood = false
    var nextfood= false
    var dosa = 0
    lateinit var notifDbHelper:NotifDBHelper

    override fun onReceive(p0: Context?, p1: Intent?) {
        val notificationIntent = Intent(p0, PaddingNottif::class.java)
        notificationIntent.putExtra("id",p1!!.extras.getLong("idPill"))
        notificationIntent.putExtra("idNottif",p1!!.extras.getLong("id"))
        val contentIntent = PendingIntent.getActivity(p0,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)
        var id = p1?.extras?.getLong("id")
        var gcalendar = GregorianCalendar()
        var timePhone:Long = (gcalendar.get(Calendar.HOUR_OF_DAY) * AlarmManager.INTERVAL_HOUR + gcalendar.get(Calendar.MINUTE) * 60000L)
        System.out.println(timePhone)
        notifDbHelper = NotifDBHelper(p0!!)
        var not = notifDbHelper.getNotifFromId(id!!.toInt())

        this.id = not.id
        this.pill = not.name
        this.period = not.period.toLong()* AlarmManager.INTERVAL_HOUR
        this.pastfood = false
        this.nextfood = false
        this.dosa = not.dosa.toInt()


        // до версии Android 8.0 API 26
        if(p1.extras.getBoolean("nextfood")){
            val builder = NotificationCompat.Builder(p0)

            builder.setDefaults(Notification.DEFAULT_SOUND)
            builder.setDefaults(Notification.DEFAULT_VIBRATE)
            builder.setContentIntent(contentIntent)
                    // обязательные настройки
                    .setSmallIcon(R.drawable.list)
                    //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                    .setContentTitle("Пора Покушать!")
                    //.setContentText(res.getString(R.string.notifytext))
                    .setContentText("А потом принять лекарство - " + pill + "\n В колличестве:"+dosa) // Текст уведомления
            // необязательные настройки



            val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // Альтернативный вариант
            // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build())

            val am = p0.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(p0, AlarmManagerBroadcastReciver::class.java)
            intent.putExtra("onetime", java.lang.Boolean.FALSE)//Задаем параметр интента
            var pi = PendingIntent.getBroadcast(p0, 0, intent, 0)
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi)
        }
        else{
            if(p1.extras.getBoolean("pastfood")){
                val builder = NotificationCompat.Builder(p0)

                builder.setDefaults(Notification.DEFAULT_SOUND)
                builder.setDefaults(Notification.DEFAULT_VIBRATE)
                builder.setContentIntent(contentIntent)
                        // обязательные настройки
                        .setSmallIcon(R.drawable.list)
                        //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                        .setContentTitle("Пора принять лекарство!")
                        //.setContentText(res.getString(R.string.notifytext))
                        .setContentText(pill + "\n В колличестве:"+dosa) // Текст уведомления
                // необязательные настройки



                val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                // Альтернативный вариант
                // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1, builder.build())

                setTimerZad(p0,System.currentTimeMillis()+AlarmManager.INTERVAL_FIFTEEN_MINUTES,id
                        ,p1!!.extras.getLong("idPill"))
            }
            else
            {
                if(p1.extras.getBoolean("pokushal")){
                    val builder = NotificationCompat.Builder(p0)

                    builder.setDefaults(Notification.DEFAULT_SOUND)
                    builder.setDefaults(Notification.DEFAULT_VIBRATE)
                    builder.setContentIntent(contentIntent)
                            // обязательные настройки
                            .setSmallIcon(R.drawable.list)
                            //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                            .setContentTitle("Пора Принять Лекарство!")
                            //.setContentText(res.getString(R.string.notifytext))
                            .setContentText( pill + "\n В колличестве:"+dosa) // Текст уведомления
                    // необязательные настройки



                    val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    // Альтернативный вариант
                    // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(1, builder.build())

                    val am = p0.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(p0, AlarmManagerBroadcastReciver::class.java)
                    intent.putExtra("onetime", java.lang.Boolean.FALSE)//Задаем параметр интента
                    var pi = PendingIntent.getBroadcast(p0, 0, intent, 0)
                    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+period, pi)

                }
                else{
                val builder = NotificationCompat.Builder(p0)

                builder.setDefaults(Notification.DEFAULT_SOUND)
                builder.setDefaults(Notification.DEFAULT_VIBRATE)
                builder.setContentIntent(contentIntent)
                        // обязательные настройки
                        .setSmallIcon(R.drawable.list)
                        //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                        .setContentTitle("Пора принять лекарство!")
                        //.setContentText(res.getString(R.string.notifytext))
                        .setContentText(pill + "\n В колличестве:"+dosa)
                        .setAutoCancel(true)// Текст уведомления
                // необязательные настройки



                val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                // Альтернативный вариант
                // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1, builder.build())

                val am = p0.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(p0, AlarmManagerBroadcastReciver::class.java)
                intent.putExtra("onetime", java.lang.Boolean.FALSE)//Задаем параметр интента
                var pi = PendingIntent.getBroadcast(p0, 0, intent, 0)
                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+period, pi)
            }
        }



    }}

    fun SetAlarm(context: Context,idNotif:Long,idPill:Long,nextfood:Boolean,pastfood:Boolean) {
        notifDbHelper = NotifDBHelper(context)
        var not = notifDbHelper.getNotifFromId(idNotif.toInt())

        this.id = not.id
        this.pill = not.name
        this.time = not.timehh.toLong() * AlarmManager.INTERVAL_HOUR + not.timemm.toLong() * 60000L
        this.period = not.period.toLong() * AlarmManager.INTERVAL_HOUR
        this.pastfood = pastfood
        this.nextfood = nextfood
        this.dosa = not.dosa.toInt()

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManagerBroadcastReciver::class.java)

        //Устанавливаем интервал срабатывания в 5 секунд.
        System.out.println(time)
        var gcalendar = GregorianCalendar()
        var timePhone:Long = (gcalendar.get(Calendar.HOUR_OF_DAY) * AlarmManager.INTERVAL_HOUR + gcalendar.get(Calendar.MINUTE) * 60000L)
        System.out.println(timePhone)
        if(pastfood){
            intent.putExtra("id", idNotif)//Задаем параметр интента
            intent.putExtra("idPill",idPill)
            intent.putExtra("pastfood",true)
            var pi = PendingIntent.getBroadcast(context, 0, intent, idNotif.toInt())
            Log.d("PASTFOOD","PASTFOOD")
            if(time>timePhone)
                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(time-timePhone - 30000L)-AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi)
            else
                am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(time+AlarmManager.INTERVAL_DAY - 30000L)-AlarmManager.INTERVAL_FIFTEEN_MINUTES , pi)
        }
        else{
            if(nextfood){
                intent.putExtra("id", idNotif)//Задаем параметр интента
                intent.putExtra("idPill",idPill)
                intent.putExtra("nextfood",true)
                Log.d("NEXTFOOD","NEXTFOOD")
                var pi = PendingIntent.getBroadcast(context, 0, intent, idNotif.toInt())

                if(time>timePhone)
                    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(time-timePhone - 30000L), pi)
                else
                    am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(time+AlarmManager.INTERVAL_DAY - 30000L) , pi)
            }
            else {
                intent.putExtra("id", idNotif)//Задаем параметр интента
                intent.putExtra("idPill",idPill)
                var pi = PendingIntent.getBroadcast(context, 0, intent, idNotif.toInt())

                if(time>timePhone)
                    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(time-timePhone - 30000L), pi)
                else
                    am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(time+AlarmManager.INTERVAL_DAY - 30000L) , pi)
            }
        }



    }

    fun CancelAlarm(context: Context) {
        val intent = Intent(context, AlarmManagerBroadcastReciver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)//Отменяем будильник, связанный с интентом данного класса
    }

    fun setOnetimeTimer(context: Context,time:Long, id:Long,idPill: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManagerBroadcastReciver::class.java)
        intent.putExtra("id", id)
        intent.putExtra("idPill",idPill)
        var pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        am.set(AlarmManager.RTC_WAKEUP, time, pi)
    }
    fun setTimerZad(context: Context,time:Long, id:Long,idPill: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManagerBroadcastReciver::class.java)
        intent.putExtra("id", id)
        intent.putExtra("idPill",idPill)
        intent.putExtra("pokushal",true)
        var pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        am.set(AlarmManager.RTC_WAKEUP, time, pi)
    }
}