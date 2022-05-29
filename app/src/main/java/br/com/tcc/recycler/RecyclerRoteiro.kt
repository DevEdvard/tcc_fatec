package br.com.tcc.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tcc.R
import br.com.tcc.databinding.RecRoteiroBinding
import br.com.tcc.model.Roteiro
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database

class RecyclerRoteiro(private val onItemClicked: (Roteiro) -> Unit, private val mContext: Context) :
    RecyclerView.Adapter<RecyclerRoteiro.RoteiroHolder>() {

    private var listaRoteiro: List<Roteiro> = ArrayList()

    inner class RoteiroHolder(val itemBinding: RecRoteiroBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(roteiro: Roteiro, onItemClicked: (Roteiro) -> Unit) {
            itemBinding.txtPrincipal.text = roteiro.nomFantasia
            itemBinding.txtSecundario.text = roteiro.bandeira

            when (roteiro.flColeta) {
                0 -> itemBinding.lnStatus.setBackgroundColor(Color.RED)
                1 -> itemBinding.lnStatus.setBackgroundColor(Color.YELLOW)
                else -> itemBinding.lnStatus.setBackgroundColor(Color.GREEN)
            }

            itemView.setOnClickListener {
                when (roteiro.flColeta) {
                    0 -> {
                        val db = Database.getInstance(mContext)
                        val dao = db.roomRoteiroDao
                        val validaCheckin = dao.validaCheckin(roteiro.id!!)

                        if (validaCheckin.isEmpty()) {
                            val alerta = AlertDialog
                                .Builder(mContext)
                                .setTitle("Check-in")
                                .setMessage("Tem certeza que deseja realizar o check-in em " + roteiro.nomFantasia + "?")
                                .setPositiveButton("Confirmar",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        dao.realizaCheckin(roteiro.id!!)
                                        onItemClicked(roteiro)
                                    })
                                .setNegativeButton("Cancelar",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        dialog.dismiss()
                                    })

                            alerta.create().show()
                            db.close()
                        } else {
                            Util.mensagemSnack(
                                itemView,
                                itemView.context.resources.getString(R.string.roteiro_alerta_checkout),
                                R.color.vermelho,
                                0)
                        }
                    }

                    1 -> {
                        onItemClicked(roteiro)
                    }

                    2 -> {
                        Util.mensagemSnack(
                            itemView,
                            itemView.context.resources.getString(R.string.roteiro_enviado),
                            R.color.verde,
                            0
                        )
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoteiroHolder {
        return RoteiroHolder(
            RecRoteiroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerRoteiro.RoteiroHolder, position: Int) {
        val roteiro = listaRoteiro[position]
        holder.bindItem(roteiro, onItemClicked)
    }

    override fun getItemCount(): Int {
        return listaRoteiro.size
    }

    fun setDataSource(dataSource: List<Roteiro>) {
        this.listaRoteiro = dataSource
    }
}