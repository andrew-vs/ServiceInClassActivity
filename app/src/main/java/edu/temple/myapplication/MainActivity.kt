package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    var timerBinder: TimerService.TimerBinder? = null

    private lateinit var buttonStart: Button
    private lateinit var editText: EditText

    val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerBinder = service as TimerService.TimerBinder

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            timerBinder = null
        }

    }

    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )


        buttonStart = findViewById(R.id.button)

        editText = findViewById(R.id.editTextText)



        buttonStart.setOnClickListener{
            val number = editText.text.toString()

            try{

                timerBinder?.start(number.toInt())
            }catch(e: Exception){
                Log.d("error", e.toString())
            }



        }




    }

    override fun onDestroy(){
        super.onDestroy()
        unbindService(serviceConnection)
    }

}