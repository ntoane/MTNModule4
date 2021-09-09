package com.example.hardwareapis

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_music.*

class MusicFragment : Fragment(R.layout.fragment_music) {
    //for playing music when seekBar changed
    lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Create MediaPlayer Object
        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.music)
        //add seekbar and set progress to 0 and max duration to duration of music
        seekBar.progress = 0
        seekBar.max = mediaPlayer.duration

        //Player button event
        playBtn.setOnClickListener {
            //Check if mediaPlayer is not playing
            if(!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                //Change button text to pause
                playBtn.text = "Pause"
                initializeSeekBar(mediaPlayer)
            } else {
                mediaPlayer.pause()
                playBtn.text = "Play"
            }
        }
        // Implement seekBar funtionality
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

    }

    private fun initializeSeekBar(mediaPlayer: MediaPlayer) {
        runnable = Runnable {
            if(mediaPlayer.isPlaying) {
                seekBar.progress = mediaPlayer.currentPosition
            }
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        //when player finish, set seekBar to 0
        mediaPlayer.setOnCompletionListener {
            playBtn.text = "Play"
            seekBar.progress = 0
        }
    }
}