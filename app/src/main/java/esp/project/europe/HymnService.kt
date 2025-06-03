package esp.project.europe

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat

class HymnService : Service() {

    private var isPlaying = false
    private var myPlayer : MediaPlayer? = null

    //No binding is needed
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //Use of on Start command
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("DEBUG", "onStartCommand")
        if (intent.getBooleanExtra(ACTION_PLAY, false)) {
            Log.d("DEBUG", "Into if for start")
            playHymn(intent)
        }
        else if (intent.getBooleanExtra(ACTION_STOP, false)) {
            Log.d("DEBUG", "Into if for stop")
            stopHymn()
        }
        return START_STICKY
    }

    //Use of on create
    override fun onCreate() {
        super.onCreate()

        //Check for the version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Create the notification channel
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Hymn reproduction",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            //Create the notification manager
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)

        }
    }

    //Use of on destroy
    override fun onDestroy() {
        stopHymn()
        super.onDestroy()
    }

    //Create a private fun for playing the hymn
    private fun playHymn(intent: Intent) {

        //If already playing, return
        if(isPlaying) return

        //Create notification, it has to be done early due to time limit of 5 seconds
        val notification = createNotification()
        startForeground(1, notification)

        //Set the flag to true
        isPlaying = true

        //Get the song name, make it into ad Identifier for the create method
        val songName = intent.getStringExtra(NATIONS_HYMN)
        val songId = resources.getIdentifier(songName, "raw", packageName)
        Log.d("DEBUG", "Got to song name: $songId")

        //Add the song
        myPlayer = MediaPlayer.create(this, songId)
        myPlayer!!.isLooping = true

        //Hold the PARTIAL_WAKE_LOCK lock while playing
        myPlayer!!.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)

        //Start the player
        myPlayer!!.start()
    }

    //Create a private fun for stopping the hymn
    private fun stopHymn() {
        if(isPlaying) {
            isPlaying = false
            myPlayer?.release()
            myPlayer = null
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    //Create a private fun for the notification
    private fun createNotification(): Notification {

        //Add name of the country in the notification
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        notificationBuilder.setContentTitle("Inno Title")
        notificationBuilder.setContentText("Description")
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationBuilder.setSmallIcon(android.R.drawable.ic_media_play)

        return notificationBuilder.build()
    }

    //Object containing the etiquette
    companion object {
        private const val CHANNEL_ID = "HymnServiceChannel"
        const val ACTION_PLAY = "play_hymn"
        const val ACTION_STOP = "stop_hymn"
        const val NATIONS_HYMN = "nations_hymn"
    }

}