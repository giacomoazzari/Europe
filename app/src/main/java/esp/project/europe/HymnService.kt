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
import androidx.core.app.NotificationManagerCompat

class HymnService : Service() {

    private var isPlaying = false
    private var myPlayer : MediaPlayer? = null

    //No binding is needed
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //Use of on Start command
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        //Two cases: start or stop the hymn
        if (intent.getBooleanExtra(ACTION_PLAY, false)) {

            playHymn(intent)
        }
        else if (intent.getBooleanExtra(ACTION_STOP, false)) {

            stopHymn()
        }
        return START_STICKY
    }

    //Use of on create
    override fun onCreate() {
        super.onCreate()

        //Create the channel
        createNotificationChannel()
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

    //Create a private fun for the notification channel
    private fun createNotificationChannel() {

        //Check API level
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Get the parameters
            val id = CHANNEL_ID
            val name = "Hymn reproduction"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            //Create the channel
            val channel = NotificationChannel(id, name, importance)

            //Create the notification manager
            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    //Create a private fun for the notification
    private fun createNotification(): Notification {

        //TODO: add pending intent for going back

        //Add name of the country in the notification
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("Title")
            .setContentText("Text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }

    //Object containing the etiquette
    companion object {
        private const val CHANNEL_ID = "HymnServiceChannel"
        const val ACTION_PLAY = "play_hymn"
        const val ACTION_STOP = "stop_hymn"
        const val NATIONS_HYMN = "nations_hymn"
    }

}