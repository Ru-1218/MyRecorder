package com.example.ru_1218.myrecoder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException
import android.content.Intent
import android.widget.Toast
import java.util.*
import java.io.File


private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class MainActivity : AppCompatActivity() {
    private var recorder: MediaRecorder? = null
    private var fileName: String = ""
    private var player: MediaPlayer? = null
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED


        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
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

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
            } catch (e: IOException){
                Log.e(LOG_TAG, "prepare() failed")
            }
            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        val record = findViewById<Button>(R.id.redord) //録音オブジェクト取得
        val stop = findViewById<Button>(R.id.stop) //録音停止オブジェクト取得
        val voicelist = findViewById<Button>(R.id.list_btn) //リストオブジェクト取得

        val listener = RecordButton() //レコードボタンリスナの設定

        record.setOnClickListener(listener)
        stop.setOnClickListener(listener)
        voicelist.setOnClickListener(listener)
    }



    //クリックイベントの設定
    private inner class RecordButton : View.OnClickListener {
        override fun onClick(v: View?) {
            Log.i(LOG_TAG, "クリック成功")

            var tmpfileDir = "${externalCacheDir?.absolutePath}"

            if(v != null){
                when(v.id){
                    //録音開始ボタン
                    R.id.redord -> {
                        val uuidString = UUID.randomUUID().toString()//クリックイベント時にuuidを生成
                        fileName = "${externalCacheDir?.absolutePath}/${uuidString}.3gp"
                        onRecord(true)
                        Toast.makeText(applicationContext, "録音中", Toast.LENGTH_LONG).show()
                    }
                    //録音停止ボタン
                    R.id.stop -> {
                        onRecord(false)
                        Toast.makeText(applicationContext, "完了", Toast.LENGTH_LONG).show()
                    }


                    R.id.list_btn -> {

                        val filePath = arrayListOf<String?>()
                        val intent = Intent(applicationContext, PlayBackActivity::class.java) //再生リスト

                        val pathList = File(tmpfileDir).list()
                        for (path in pathList){
                            val path: String = "${externalCacheDir?.absolutePath}/${path}"
                            filePath.add(path)
                        }

                        intent.putExtra("voice_file", filePath) //2画面に送るデータを格納
                        startActivity(intent) //2画面の起動
                    }
                }
            }

        }

    }
}