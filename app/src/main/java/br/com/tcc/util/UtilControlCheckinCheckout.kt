package br.com.tcc.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import br.com.tcc.controller.AppCompat
import br.com.tcc.model.Roteiro
import br.com.tcc.util.database.Database

class UtilControlCheckinCheckout(val loja: Roteiro, val mContext: Context) : AppCompat() {

    private lateinit var btnPositive: Button
    private lateinit var btnNegative: Button
    private lateinit var mAlerta: AlertDialog

    fun autenticarCheckIn() : Int {
        var retorno = 0
        val db = Database.getInstance(mContext)
        val dao = db.roomRoteiroDao

        val list = dao.validaCheckin(loja.id!!)
        if (!list.isEmpty()) {
            alertaCheckInEfetuado()
        } else {
            alertaCheckInConfirmacao()
        }

        db.close()
        return retorno
    }

    private fun alertaCheckInConfirmacao() {
        val builder = AlertDialog
            .Builder(mContext)
            .setIcon(null)
            .setTitle("Check-in")
            .setCancelable(false)
            .setMessage("Tem certeza que deseja realizar o check-in em " + loja.nomFantasia + "?")
            .setPositiveButton("Confirmar", null)
            .setNegativeButton("Cancelar", null)

        mAlerta = builder.create()
        mAlerta.show()
    }

    private fun alertaCheckInEfetuado() {
        val builder = AlertDialog
            .Builder(mContext)
            .setIcon(null)
            .setTitle("Check-in")
            .setCancelable(false)
            .setMessage("Não é possivel efetuar o check-in, pois ainda há uma pesquisa sendo realizada em outra loja.")
            .setNegativeButton("Ok", null)

        mAlerta = builder.create()
        mAlerta.show()
    }

    private fun realizarCheckIn() {
        val db = Database.getInstance(mContext)
        val dao = db.roomRoteiroDao
        dao.realizaCheckin(loja.id!!, Util.dataHora())
        db.close()
    }

    override fun onClick(v: View?) {
        when (v) {
            btnPositive -> realizarCheckIn()
            btnNegative -> mAlerta.dismiss()
        }
    }

    override fun onShow(dialog: DialogInterface?) {
        btnPositive = mAlerta.getButton(Dialog.BUTTON_POSITIVE)
        btnPositive.setOnClickListener(this)
        btnNegative = mAlerta.getButton(Dialog.BUTTON_NEGATIVE)
        btnNegative.setOnClickListener(this)
    }
}