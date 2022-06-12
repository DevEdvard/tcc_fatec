package br.com.tcc.util

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import br.com.tcc.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.util.*

object Util {

    @Throws(Exception::class)
    fun validaEditText(edt: EditText, context: Context): Boolean {
        if (edt.text.toString().length <= 0) {
            edt.setError(context.resources.getString(R.string.msg_campo_obrigatorio))
            return false
        }

        return true
    }


    @Throws(Exception::class)
    fun validaEditTextInteiro(edt: EditText, context: Context, valida_zero: Boolean): Boolean {
        if (edt.text.toString().isEmpty()) {
            edt.setError(context.resources.getString(R.string.msg_campo_obrigatorio))
            return false
        }

        if (valida_zero && edt.text.toString().toInt() == 0) {
            edt.setError(context.resources.getString(R.string.msg_valor_maior_zero))
            return false
        }

        return true
    }

    @Throws(Exception::class)
    fun validaEditTextDouble(edt: EditText, context: Context, valida_zero: Boolean): Boolean {
        if (edt.text.toString().isEmpty()) {
            edt.setError(context.resources.getString(R.string.msg_campo_obrigatorio))
            return false
        }

        if (valida_zero && edt.text.toString().toDouble() == 0.0) {
            edt.setError(context.resources.getString(R.string.msg_valor_maior_zero))
            return false
        }

        return true
    }

    @Throws(Exception::class)
    fun mensagemSnack(v: View, mensagem: String, idColor: Int?, icone: Int?) {
        val mSnake = Snackbar.make(v, mensagem, Snackbar.LENGTH_SHORT)
        val vsnaker = mSnake.view
        vsnaker.setBackgroundResource(idColor!!)
        val txtSnaker =
            vsnaker.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        txtSnaker.setCompoundDrawablesWithIntrinsicBounds(0, 0, icone ?: 0, 0)
        txtSnaker.gravity = Gravity.CENTER
        txtSnaker.maxLines = 4
        mSnake.duration = 10000
        mSnake.setActionTextColor(Color.WHITE)
        mSnake.setAction("OK") { mSnake.dismiss() }
        mSnake.show()
    }

    fun dataHora(): String {
        val data = Date()
        val formatador = DateFormat.getInstance()
        formatador.format(data)
        val horaFormatada = formatador.format(data)

        return horaFormatada
    }

    /**
     * RETORNA HASH MAP
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun retornaHashJson(type: String, vararg params: Any): HashMap<String, String> {
        val mHash = HashMap<String, String>()

        if (params.size > 0) {
            for (`object` in params) {
                val key = (`object` as String).split(type.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (key.size >= 2)
                    mHash[key[0]] = key[1]

            }
        }
        return mHash
    }

    fun exibirFoto(imagemView: ImageView, url: String, context: Context) {
        Glide.with(context)
            .load(url)
            .into(imagemView)
    }
}