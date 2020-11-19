package com.example.ru_1218.myrecoder


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.widget.Toast
import android.media.MediaPlayer
import android.util.Log
import java.io.IOException


private const val LOG_TAG = "AudioRecordTest"



class DialogEvent(playback_path: String?, uuid: String) : DialogFragment() {
    private var path:String? = playback_path
    private var uuid:String  = uuid
    private var player: MediaPlayer? = null



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        //ダイアログビルダを生成
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(uuid)
        builder.setPositiveButton(R.string.play_back, DialogButtonClickListener())

        return builder.create()
    }

    private inner class DialogButtonClickListener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            //トーストメッセージ用文字列変数を用意
            var msg = ""

            when(which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    //再生
                    msg = "再生中: " + uuid
                    onPlay(true)
                }
            }

            //トーストの表示
             Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        }

    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }


    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(path)
                prepare()
                start()
            } catch(e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }
}