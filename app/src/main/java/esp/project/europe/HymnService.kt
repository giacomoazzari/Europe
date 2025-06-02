package esp.project.europe

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager

class HymnService : Service() {

    private var isPlaying = false
    private var myPlayer : MediaPlayer? = null

    //No binding is needed
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //Use of on Start command
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
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
        //TODO: Add notification
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

        //Set the flag to true
        isPlaying = true

        //Get the song name
        val songName = Uri.parse(intent.getStringExtra(NATIONS_HYMN))

        //Add the song
        myPlayer = MediaPlayer.create(this, songName)
        myPlayer!!.isLooping = true

        //Hold the PARTIAL_WAKE_LOCK lock while playing
        myPlayer!!.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)

        //Start the player
        myPlayer!!.start()


        //TODO: Add notification
    }

    //Create a private fun for stopping the hymn
    private fun stopHymn() {
        if(isPlaying) {
            isPlaying = false
            myPlayer?.release()
            myPlayer = null
            stopForeground(STOP_FOREGROUND_REMOVE)
        }
    }

    //Object containing the etiquette
    companion object {
        private const val CHANNEL_ID = "HymnServiceChannel"
        const val ACTION_PLAY = "play_hymn"
        const val ACTION_STOP = "stop_hymn"
        const val NATIONS_HYMN = "nations_hymn"
    }

}