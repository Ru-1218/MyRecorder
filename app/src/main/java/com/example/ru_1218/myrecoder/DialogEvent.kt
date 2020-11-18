package com.example.ru_1218.myrecoder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.widget.Toast


class DialogEvent(playback_path: String) : DialogFragment() {
    private var path:String = playback_path

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        //ダイアログビルダを生成
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(path)
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
                    msg = "再生中"
                }
            }

            //トーストの表示
             Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        }

    }
}