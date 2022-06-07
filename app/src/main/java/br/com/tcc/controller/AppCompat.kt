package br.com.tcc.controller

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class AppCompat : AppCompatActivity(), CallBack_Projeto, View.OnClickListener,
    DialogInterface.OnShowListener {


    override fun onClick(v: View?) {

    }

    override fun onRetorno(aBoolean : Boolean, mensagem : String) {
        TODO("Not yet implemented")
    }

    override fun onShow(dialog: DialogInterface?) {
        TODO("Not yet implemented")
    }
}