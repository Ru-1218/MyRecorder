package com.example.ru_1218.myrecoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

class PlayBackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_back)

        //録音メイン画面から取得したファイルを表示
        val fileVoice = intent.getStringExtra("fileVoice")
        val voiceName = findViewById<ListView>(R.id.voiceListMenu)

        //simpleadapterで使用するMutableObjectを用意
        val VoiceList: MutableList<MutableMap< String, String>> = mutableListOf()

//        var menu = mutableMapOf<>()

    }
}