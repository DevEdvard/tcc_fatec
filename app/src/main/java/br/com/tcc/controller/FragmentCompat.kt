package br.com.tcc.controller

import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.DialogFragment

abstract class FragmentCompat : DialogFragment(), DialogInterface.OnShowListener,
    View.OnClickListener {

    override fun onClick(v: View?) {

    }
}