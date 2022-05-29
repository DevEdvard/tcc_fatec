package br.com.tcc.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tcc.R
import br.com.tcc.databinding.RecItemMenuColetaBinding
import br.com.tcc.model.Coleta
import br.com.tcc.model.Roteiro
import br.com.tcc.util.database.Database

class RecyclerMenuColeta(
    private val onItemClicked: (Coleta) -> Unit,
    private val mContext: Context,
    val loja: Roteiro,
) :
    RecyclerView.Adapter<RecyclerMenuColeta.MenuColetaHolder>() {

    private var listaMenuColetas: List<Coleta> = ArrayList()

    inner class MenuColetaHolder(itemBinding: RecItemMenuColetaBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(coleta: Coleta, onItemClicked: (Coleta) -> Unit) {
            val db = Database.getInstance(mContext)

            if (coleta.desColeta.equals("COLETA_PRODUTO") && coleta.staColeta == 1) {
                val dao = db.roomSkuDao
                val validaColeta = dao.selectModuloColetado(loja.id!!)

                if (!validaColeta.isEmpty()) {
                    itemView.setBackgroundResource(R.drawable.menu_produto)

                } else {
                    itemView.setBackgroundResource(R.drawable.menu_produto_ok)
                }
            }

            itemView.setOnClickListener {
                val dao = db.roomRoteiroDao
                val validaStatus = dao.select(loja.id!!)

                if (validaStatus.flColeta == 1) {
                    onItemClicked(coleta)
                } else {
                    val alerta = AlertDialog
                        .Builder(mContext)
                        .setTitle("Check-out")
                        .setMessage("Já foi realizado o check-out.")
                        .setNeutralButton("Ok",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })

                    alerta.create().show()
                }
            }
            db.close()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuColetaHolder {
        return MenuColetaHolder(
            RecItemMenuColetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuColetaHolder, position: Int) {
        val coleta = listaMenuColetas[position]
        holder.bindItem(coleta, onItemClicked)
    }

    override fun getItemCount(): Int {
        return listaMenuColetas.size
    }

    fun setDataSource(roteiro: Roteiro) {

        listaColetas(mContext)
        val lista = ArrayList<Coleta>()

        if (roteiro.flProduto == 1) {
            val db = Database.getInstance(mContext)
            val dao = db.roomColetaDao


            val coletaProduto = dao.selectColeta("COLETA_PRODUTO")
            if (coletaProduto.staColeta == 1) {
                lista.add(coletaProduto)
            }
            db.close()
        }

        if (roteiro.flFotoExecucao == 1) {
            val db = Database.getInstance(mContext)
            val dao = db.roomColetaDao

            val coletaFotoExecucao = dao.selectColeta("COLETA_FOTO_EXECUCAO")
            if (coletaFotoExecucao.staColeta == 1) {
                lista.add(coletaFotoExecucao)
            }
            db.close()
        }

        this.listaMenuColetas = lista
    }

    private fun listaColetas(context: Context) {
        val db = Database.getInstance(context)
        val dao = db.roomColetaDao

        dao.deleteColetas()
        dao.insertColetas()
        db.close()
    }
}