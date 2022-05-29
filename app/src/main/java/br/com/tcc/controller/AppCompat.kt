package br.com.tcc.controller

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class AppCompat : AppCompatActivity(), View.OnClickListener,
    DialogInterface.OnShowListener {


    override fun onClick(v: View?) {

    }
}