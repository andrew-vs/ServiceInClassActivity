package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button


class MainActivity : AppCompatActivity() {

    var timerBinder: TimerService.TimerBinder? = null


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




    }

    override fun onDestroy(){
        super.onDestroy()
        unbindService(serviceConnection)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.action_start -> timerBinder?.start(100, handler)

            R.id.action_pause -> timerBinder?.pause()

            R.id.action_stop -> timerBinder?.stop()
        }
        return super.onOptionsItemSelected(item)
    }
}