package com.example.ru_1218.myrecoder

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.io.File
import kotlin.IllegalStateException


class EditFileName(playback_path: String?, uuid: String, intent: Intent): DialogFragment(){
    private var path:String = playback_path as String
    private var uuid:String  = uuid
    private var intent = intent

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.edit_filename, null))
                .setMessage(uuid)
                .setTitle("ファイル名を変更する")
                .setPositiveButton("変更", DialogInterface.OnClickListener { dialog, id ->
                    var edit = getDialog()?.findViewById<EditText>(R.id.edit_filename)
                    var name = edit?.text.toString() //入力値を受け取り

                    var slicePath: List<String> = path.split("/")
                    var getParentDir= slicePath.dropLast(1)
                    var dir: String = getParentDir.joinToString("/")

                    File(dir, "$uuid.3gp")
                        .copyTo(File(dir, "$name.3gp"), true)

//                    元のファイルの削除
                    File(dir, "$uuid.3gp").delete()
                    Toast.makeText(activity, "名前を変更しました", Toast.LENGTH_LONG).show()
                    startActivity(intent)
                    dialog.dismiss()
                })
                .setNegativeButton("閉じる",DialogInterface.OnClickListener{ dialog, id ->
                    getDialog()?.cancel()
                })

            builder.create()

        }?: throw IllegalStateException("Activrt Cannot be null")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

