package br.com.tcc.recycler

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tcc.activity.coleta.produto.fragment.ColetaProdutoFragment
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.RecItemGenericBinding
import br.com.tcc.model.Sku
import br.com.tcc.util.database.Database

class RecyclerColetaProduto(private val onItemClicked: (Sku) -> Unit, context: Context, codRoteiro: Int) :
    RecyclerView.Adapter<RecyclerColetaProduto.ColetaProdutoHolder>() {

    val roteiro = codRoteiro
    val mContext = context
    val activty = mContext as AppCompat
    private var listaSku: List<Sku> = ArrayList()

    inner class ColetaProdutoHolder(val itemBinding: RecItemGenericBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(sku: Sku, onItemClicked: (Sku) -> Unit, position: Int) {
            itemBinding.txtPrincipal.text = sku.desc
            itemBinding.txtSecundario.text = sku.marca

            when (sku.flColetado) {
                0 -> itemBinding.lnStatus.setBackgroundColor(Color.RED)
                1 -> itemBinding.lnStatus.setBackgroundColor(Color.GREEN)
            }

            itemView.setOnClickListener {
                onItemClicked(sku)
                if (sku.flColetado == 1) {

                    val db = Database.getInstance(mContext)
                    val dao = db.roomColetaProdutoDao

                    sku.id?.let {
                        val coletaProduto = dao.selectColetaProduto(it)
                        val bundle = Bundle()
                        bundle.putSerializable("sku", sku)
                        bundle.putSerializable("posicao", position)
                        val dialog = ColetaProdutoFragment(coletaProduto.idProduto!!, mContext,
                            roteiro)
                        dialog.arguments = bundle
                        dialog.show(activty.supportFragmentManager, "coletaProdutoDialog")
                        db.close()
                    }
                } else {
                    val bundle = Bundle()
                    bundle.putSerializable("sku", sku)
                    bundle.putSerializable("posicao", position)
                    val dialog = ColetaProdutoFragment(sku.id!!, mContext, roteiro)
                    dialog.arguments = bundle
                    dialog.show(activty.supportFragmentManager, "coletaProdutoDialog")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColetaProdutoHolder {
        return ColetaProdutoHolder(
            RecItemGenericBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ColetaProdutoHolder, position: Int) {
        val sku = listaSku[position]
        holder.bindItem(sku, onItemClicked, position)
    }

    override fun getItemCount(): Int {
        return listaSku.size
    }

    fun setDataSource(dataSource: List<Sku>) {
        this.listaSku = dataSource
    }
}
