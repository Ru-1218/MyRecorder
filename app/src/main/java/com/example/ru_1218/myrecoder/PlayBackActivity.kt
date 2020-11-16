package com.example.ru_1218.myrecoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter

class PlayBackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_back)

        //録音メイン画面から取得したファイルを表示
        val fileVoice = intent.getStringExtra("voice_file")
        val voiceName = findViewById<ListView>(R.id.voiceListMenu)


        val VoiceList: MutableList<MutableMap<String,String?>> = mutableListOf()
        //simpleadapterで使用するMutableObjectを用意
        var voiceMenu = mutableMapOf("name" to fileVoice)
        VoiceList.add(voiceMenu)

        val from = arrayOf("name")

        val to = intArrayOf(android.R.id.text1)

        //simple adapter
        val adapter = SimpleAdapter(applicationContext, VoiceList, android.R.layout.simple_list_item_2, from, to)
        //アダプタの登録
        voiceName.adapter = adapter

    }
}