//package br.com.tcc.recycler.testeRecycler
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import br.com.tcc.R
//
//class TesteRecycler(private val onItemClicked: (TesteObjectRecycler) -> Unit) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private var itens: List<TesteObjectRecycler> = ArrayList()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return testeViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.rec_item_generic, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is testeViewHolder -> {
//                holder.bind(itens[position], onItemClicked)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return itens.size
//    }
//
//    fun setDataSource(testeObjectRecycler: ArrayList<TesteObjectRecycler>) {
//        this.itens = testeObjectRecycler
//    }
//
//    class testeViewHolder constructor(
//        itemView: View,
//    ) : RecyclerView.ViewHolder(itemView) {
//
//        private val descSku = itemView.txt_principal
//        private val marca = itemView.txt_secundario
//
//        fun bind(testeObjectRecycler: TesteObjectRecycler, onItemClicked: (TesteObjectRecycler) -> Unit) {
//
//            descSku.text = testeObjectRecycler.desSku
//            marca.text = testeObjectRecycler.marca
//
//            itemView.setOnClickListener {
//                onItemClicked(testeObjectRecycler)
//            }
//        }
//    }
//}