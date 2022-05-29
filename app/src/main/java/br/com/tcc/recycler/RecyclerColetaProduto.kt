package br.com.tcc.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tcc.databinding.RecItemGenericBinding
import br.com.tcc.model.Sku

class RecyclerColetaProduto(private val onItemClicked: (Sku) -> Unit) :
    RecyclerView.Adapter<RecyclerColetaProduto.ColetaProdutoHolder>() {

    private var listaSku: List<Sku> = ArrayList()

    inner class ColetaProdutoHolder(val itemBinding: RecItemGenericBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(sku: Sku, onItemClicked: (Sku) -> Unit) {
            itemBinding.txtPrincipal.text = sku.desc
            itemBinding.txtSecundario.text = sku.marca

            when (sku.flColetado) {
                0 -> itemBinding.lnStatus.setBackgroundColor(Color.RED)
                1 -> itemBinding.lnStatus.setBackgroundColor(Color.GREEN)
            }

            itemView.setOnClickListener {
                onItemClicked(sku)
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
        holder.bindItem(sku, onItemClicked)
    }

    override fun getItemCount(): Int {
        return listaSku.size
    }

    fun setDataSource(dataSource: List<Sku>) {
        this.listaSku = dataSource
    }
}
