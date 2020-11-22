package com.example.ru_1218.myrecoder


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import java.io.File


class PlayBackActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //録音メイン画面から取得したファイルを表示
        val filePath = arrayListOf<String?>()

        val pathList = File("${externalCacheDir?.absolutePath}").list()
        for (path in pathList){
            val path: String = "${externalCacheDir?.absolutePath}/${path}"
            filePath.add(path)
        }
        val intent = Intent(applicationContext, EditFileNameActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_back)
    }

    override fun onResume() {
        //録音メイン画面から取得したファイルを表示
        val filePath = arrayListOf<String?>()

        val pathList = File("${externalCacheDir?.absolutePath}").list()
        for (path in pathList){
            val path: String = "${externalCacheDir?.absolutePath}/${path}"
            filePath.add(path)
        }


        val voiceName = findViewById<ListView>(R.id.voiceListMenu)
        voiceName.onItemClickListener = ListItemClickListener(filePath)

        val VoiceList: MutableList<MutableMap<String,String>> = mutableListOf()

        filePath.forEach { path ->
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
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
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
        return null
    }
}