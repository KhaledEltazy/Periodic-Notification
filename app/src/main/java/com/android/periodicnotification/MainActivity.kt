package com.android.periodicnotification




import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.periodicnotification.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)


        binding.btnSendNotification.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(currentHour)
                .setMinute(currentMinute)
                .setTitleText("Set Notification Time")
                .build()
            timePicker.show(supportFragmentManager,"1")
            timePicker.addOnPositiveButtonClickListener {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.hour)
                calendar.set(Calendar.MINUTE, timePicker.minute)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)

                val intent = Intent(applicationContext, NOTIFICATION_SERVICE::class.java)

                val pendingIntent = if(Build.VERSION.SDK_INT >= 23) {
                    PendingIntent.getBroadcast(
                        applicationContext,
                        100,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                } else {
                    PendingIntent.getBroadcast(
                        applicationContext,
                        100,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_HOUR,
                    pendingIntent
                )

                Toast.makeText(applicationContext,"The alarm has set", Toast.LENGTH_LONG).show()
            }
        }

    }


}