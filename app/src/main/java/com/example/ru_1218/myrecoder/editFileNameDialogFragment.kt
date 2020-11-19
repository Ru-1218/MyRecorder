package com.example.ru_1218.myrecoder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

class editFileNameDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // ダイアログビルダを生成
        val builder = AlertDialog.Builder(activity)

        val inflater = requireActivity().layoutInflater;
        //ダイアログのタイトルを設定
        builder.setTitle("Edit")
        //pathとファイル名を受け取ってそれぞれ編集できるようにする
        builder.setView(inflater.inflate(R.layout.edit_filename, null))
    }
}