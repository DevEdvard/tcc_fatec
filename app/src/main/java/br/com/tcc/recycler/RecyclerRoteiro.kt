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
            itemBinding.apply {

                txtPrincipal.text = roteiro.nomFantasia
                txtSecundario.text = roteiro.bandeira

                when (roteiro.flColeta) {
                    0 -> {
                        lnStatus.setBackgroundColor(Color.RED)
                    }
                    1, 2 -> {
                        lnStatus.setBackgroundColor(Color.YELLOW)
                        txtCheckinHorario.text = roteiro.checkin

                        if (!roteiro.checkout!!.isEmpty()) {
                            txtCheckoutHorario.text = roteiro.checkout
                        }

                    }
                    else -> {
                        lnStatus.setBackgroundColor(Color.GREEN)
                        txtCheckinHorario.text = roteiro.checkin
                        txtCheckoutHorario.text = roteiro.checkout
                    }
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
                                            dao.realizaCheckin(roteiro.id!!, Util.dataHora())
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

                        1, 2 -> {
                            onItemClicked(roteiro)
                        }

                        3 -> {
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