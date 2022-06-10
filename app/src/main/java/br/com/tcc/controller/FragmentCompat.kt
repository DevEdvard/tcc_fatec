package br.com.tcc.controller

import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.DialogFragment

abstract class FragmentCompat : DialogFragment(), DialogInterface.OnShowListener,
    View.OnClickListener, CallBack_Projeto {

    override fun onClick(v: View?) {

    }

    override fun onShow(dialog: DialogInterface?) {
        TODO("Not yet implemented")
    }

    override fun onRetorno(aBoolean : Boolean, mensagem : String) {
        TODO("Not yet implemented")
    }

    override fun onRetorno() {
        TODO("Not yet implemented")
    }

    override fun onRetorno(posicao: Int) {
        TODO("Not yet implemented")
    }

}