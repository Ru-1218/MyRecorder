package com.example.ru_1218.myrecoder


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class PlayBackActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_back)

        //録音メイン画面から取得したファイルを表示
        val fileVoice = intent.getSerializableExtra("voice_file") as ArrayList<*>


        val voiceName = findViewById<ListView>(R.id.voiceListMenu)
        voiceName.onItemClickListener = ListItemClickListener(fileVoice)

        val VoiceList: MutableList<MutableMap<String,String>> = mutableListOf()
        //simpleadapterで使用するMutableObjectを用意



        fileVoice.forEach { path ->
            print(path)
            var path = path as String
            var voiceMenu = getFileName(path)
            VoiceList.add(voiceMenu)
        }


        val from = arrayOf("name")

        val to = intArrayOf(R.id.PlayBacklListData)

        //simple adapter
        val adapter = SimpleAdapter(applicationContext, VoiceList, R.layout.playback_list_row, from, to)
        //アダプタの登録
        voiceName.adapter = adapter

    }


    private inner class createEditMenu(dataList: ArrayList<*>): AdapterView.OnItemClickListener {
        private var list: ArrayList<*> = dataList
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item= parent?.getItemAtPosition(position) as MutableMap<String, String>
            val name = item["name"] as String
            val dataPath = sameNameFilePath(list, name)

            //datapathの名前を編集するダイアログの生成



        }

    }




    private fun getFileName(path: String): MutableMap<String, String> {
        var file: List<String> = path.split("/")
        var fileName = file.takeLast(1).first()
        var uuid: String = fileName.replace(".3gp", "")
        var voiceMenu = mutableMapOf("name" to uuid)
        return voiceMenu
    }

    private fun checkFileName(path: String): String{
        var file: List<String> = path.split("/")
        var fileName = file.takeLast(1).first()
        var uuid: String = fileName.replace(".3gp", "")
        return uuid
    }

    private fun sameNameFilePath(datas: ArrayList<*>, fileName: String): String? {
        datas.forEach { path ->
            print(path)
            var path = path
            var voiceMenu: String = checkFileName(path as String)
            if (voiceMenu == fileName) {
                return path
            }

        }
        return "teset"
    }

    private inner class ListItemClickListener(dataList: ArrayList<*>) : AdapterView.OnItemClickListener {
        private var list: ArrayList<*> = dataList

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item= parent?.getItemAtPosition(position) as MutableMap<String, String>

            val uuid = item["name"] as String
            val dataPath = sameNameFilePath(list, uuid)
            //ダイアログフラグメントオブジェクトの作成
            val dialogFragment = DialogEvent(dataPath,uuid)
            dialogFragment.show(supportFragmentManager, "DialogEvent")

        }



    }

}