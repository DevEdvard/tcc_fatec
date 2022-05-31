package br.com.tcc.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.tcc.R
import br.com.tcc.model.Justificativa

class AdapterJustificativa
constructor(private var context: Context, private var lista: ArrayList<Justificativa>) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        var mView = convertView
        try {
            if (mView == null) {
                mView =
                    LayoutInflater.from(context).inflate(R.layout.adapter_generic, parent, false)
                holder = ViewHolder(mView)
                mView.tag = holder
            } else {
                holder = mView.tag as ViewHolder
            }

            val sbHtml = StringBuilder()
            sbHtml.append(String.format("%s", lista[position].descricao.toString()))

            holder.nome.text = Html.fromHtml(sbHtml.toString())
        } catch (err: Exception) {
        }

        return mView!!
    }


    override fun getItem(position: Int): Any {
        return lista[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return lista.size
    }

    internal inner class ViewHolder(v: View) {
        var nome: TextView

        init {
            this.nome = v.findViewById(R.id.nome) as TextView
        }
    }

}