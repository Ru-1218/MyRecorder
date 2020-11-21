package com.example.ru_1218.myrecoder

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class EditFileName(playback_path: String?, uuid: String): DialogFragment() {
    private var path:String? = playback_path
    private var uuid:String  = uuid
    val inflater = requireActivity().layoutInflater;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //ダイアログビルダを生成
        val builder = AlertDialog.Builder(activity)
        //ダイアログのタイトルを設定
        builder.setTitle("Edit File Name")
        //ビューの読み込み

        builder.setView(inflater.inflate(R.layout.edit_filename, null))
        builder.setMessage(uuid)

        //ボタンイベント
        builder.setPositiveButton(R.string.MenuListEdit, DialogButtonClickListener())

        return builder.create()
    }

    private inner class DialogButtonClickListener: DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {


            when(which){

                DialogInterface.BUTTON_POSITIVE->{
                    //ファイル名の変更もといパス情報も書き換える


                }

            }

        }

    }
}