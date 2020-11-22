package com.example.ru_1218.myrecoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class EditFileNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_file_name)

        val fileNmae = intent.getSerializableExtra("EditNames") as ArrayList<*>

        val fileList = findViewById<ListView>(R.id.voiceListMenu)
        fileList.onItemClickListener = ListItemClickListener(fileNmae)


        val voiceList: MutableList<MutableMap<String, String>> = mutableListOf()

        fileNmae.forEach {
            path ->
            var path = path as String
            var voiceMenu = getFileName(path)
            voiceList.add(voiceMenu)

        }

        val from = arrayOf("name")
        val to = intArrayOf(R.id.EditFileName_row)

        val adapter = SimpleAdapter(applicationContext, voiceList, R.layout.activity_edit_row, from, to)
        fileList.adapter = adapter
    }

    private fun getFileName(path: String): MutableMap<String, String> {
        var file: List<String> = path.split("/")
        var fileName = file.takeLast(1).first()
        var uuid: String = fileName.replace(".3gp", "")
        var voiceMenu = mutableMapOf("name" to uuid)
        return voiceMenu
    }

    private inner class ListItemClickListener(dataList: ArrayList<*>) : AdapterView.OnItemClickListener {
        private var data = dataList
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item= parent?.getItemAtPosition(position) as MutableMap<String, String>

            val uuid = item["name"] as String
            val dataPath = sameNameFilePath(data, uuid)
            val dialogFragment = EditFileName(dataPath, uuid)
            dialogFragment.show(supportFragmentManager, "DialogEvent")

        }

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
        return "test"
    }




}