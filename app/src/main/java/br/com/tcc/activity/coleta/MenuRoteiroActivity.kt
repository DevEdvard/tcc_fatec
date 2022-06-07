package br.com.tcc.activity.coleta

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tcc.R
import br.com.tcc.activity.coleta.fragment.JustificativaFragment
import br.com.tcc.activity.coleta.produto.ColetaProdutoActivity
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.ActivityMenuRoteiroBinding
import br.com.tcc.model.Pesquisa
import br.com.tcc.model.Roteiro
import br.com.tcc.recycler.RecyclerMenuColeta
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database

class MenuRoteiroActivity : AppCompat() {

    private var recyclerMenu: RecyclerMenuColeta? = null
    private lateinit var _binding: ActivityMenuRoteiroBinding
    private lateinit var loja: Roteiro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMenuRoteiroBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        controles()
    }

    override fun onResume() {
        super.onResume()
        recyclerMenu!!.notifyDataSetChanged()
    }

    private fun controles() {
        loja = getExtras()
        configuraToolBar()
        iniciarRecycler()
        setarDadosColeta()
        setarBtnPesquisa()
    }

    private fun configuraToolBar() {
        setupToolbar(loja.nomFantasia)
    }


    private fun setarBtnPesquisa() {
        val db = Database.getInstance(this)
        val daoRoteiro = db.roomRoteiroDao
        val daoPesquisa = db.roomPesquisaDao
        val mPesquisa = daoPesquisa.selectId(loja.id!!)
        val roteiro = daoRoteiro.selectId(loja.id!!)

        _binding.apply {

            imgJustificativa.setBackgroundResource(R.drawable.menu_justificativa)
            imgCheckout.setBackgroundResource(R.drawable.menu_checkout)
            imgTransmissao.setBackgroundResource(R.drawable.menu_transmissao)


            if (roteiro.flColeta != 1 && mPesquisa.codJustificativa != 0) {
                imgJustificativa.setBackgroundResource(R.drawable.menu_justificativa_ok)
                imgCheckout.setBackgroundResource(R.drawable.menu_checkout_ok)
            } else if (roteiro.flColeta != 1 && mPesquisa.codJustificativa == 0) {
                imgCheckout.setBackgroundResource(R.drawable.menu_checkout_ok)
            }

            imgJustificativa.setOnClickListener(this@MenuRoteiroActivity)
            imgCheckout.setOnClickListener(this@MenuRoteiroActivity)
            imgTransmissao.setOnClickListener(this@MenuRoteiroActivity)
        }

        db.close()
    }

    private fun getExtras(): Roteiro {
        val extra = intent.getIntExtra("ROTEIRO", 0)
        val db = Database.getInstance(this)
        val dao = db.roomRoteiroDao
        val roteiro = dao.selectId(extra)
        db.close()
        return roteiro
    }

    private fun setupToolbar(title: String?) {
        _binding.toolbarMenuRoteiro.title = title
    }

    private fun iniciarRecycler() {
        setOnItemClick()
        _binding.recyclerViewMenuRoteiro.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = recyclerMenu
        }
    }

    private fun setarDadosColeta() {
        val db = Database.getInstance(this)
        val dao = db.roomRoteiroDao

        val dataSource = dao.selectLojaRoteiro(loja.id!!)
        recyclerMenu!!.setDataSource(dataSource)
        db.close()
    }

    override fun onClick(v: View?) {
        val db = Database.getInstance(this)
        val daoRoteiro = db.roomRoteiroDao
        val daoPesquisa = db.roomPesquisaDao
        val daoSku = db.roomSkuDao

        val lojaStatus = daoPesquisa.selectId(loja.id!!).codJustificativa
        val validaCheckout = daoRoteiro.selectId(loja.id!!)

        when (v) {
            _binding.imgJustificativa -> {
                if (validaCheckout.flColeta == 1) {
                    if (validaCheckout.flColeta == 1) {
                        val fragmentJus = JustificativaFragment(loja.id!!)
                        fragmentJus.show(supportFragmentManager, "justificativaDialog")
                    } else {
                        aviso("Justificativa", "A loja já foi justificada.")
                    }
                } else if (validaCheckout.flColeta != 1 && lojaStatus != 0) {
                    aviso("Justificativa", "Loja já justificada.")
                } else {
                    aviso("Justificativa",
                        "Não é possivel justificar, pois já foi realizado check-out na loja.")
                }
            }

            _binding.imgCheckout -> {
                val validaColeta = daoSku.selectModuloColetado(loja.id!!)

                if (validaCheckout.flColeta == 1) {
                    if (!validaColeta.isEmpty()) {
                        aviso("Check-out", "A pesquisa ainda não foi realizada.")
                    } else {
                        val alerta = AlertDialog
                            .Builder(this)
                            .setTitle("Check-out")
                            .setMessage("Tem certeza que deseja realizar o check-out?")
                            .setPositiveButton("Confirmar",
                                DialogInterface.OnClickListener { dialog, which ->
                                    daoRoteiro.realizaCheckout(loja.id!!, Util.dataHora())
                                    salvaPesquisa()
                                    _binding.imgCheckout.setBackgroundResource(R.drawable.menu_checkout_ok)
                                    dialog.dismiss()
                                })
                            .setNegativeButton("Cancelar",
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                })
                        alerta.create().show()
                    }
                } else if (validaCheckout.flColeta != 1 && lojaStatus == 0) {
                    aviso("Check-out", "O check-out já foi realizado.")
                } else {
                    aviso("Check-out",
                        "Não é possivel realizar o check-out, pois loja já foi justificada.")
                }
            }
            _binding.imgTransmissao -> Toast.makeText(this, "Clicou mesmo", Toast.LENGTH_SHORT)
                .show()
        }
        db.close()
    }

    private fun aviso(title: String?, message: String?) {
        val alerta = AlertDialog
            .Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton("Ok",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        alerta.create().show()
    }

    private fun salvaPesquisa() {
        val db = Database.getInstance(this)
        val daoPesquisa = db.roomPesquisaDao
        val daoUsuario = db.roomUsuarioDao
        val daoRoteiro = db.roomRoteiroDao
        val roteiro = daoRoteiro.selectId(loja.id!!)

        val mPesquisa = Pesquisa()

        mPesquisa.apply {
            codUsuario = daoUsuario.select()!!.id
            codLoja = loja.id
            codJustificativa = 0
            desJustificativa = ""
            coletaProduto = roteiro.flProduto
            coletaFotoExecucao = roteiro.flFotoExecucao
            checkin = roteiro.checkin
            checkout = roteiro.checkout
        }

        daoPesquisa.insert(mPesquisa)
        db.close()
    }

    private fun setOnItemClick() {
        recyclerMenu = RecyclerMenuColeta({ itemClicked ->
            val intent = Intent(this@MenuRoteiroActivity, ColetaProdutoActivity::class.java)
            intent.putExtra("LOJA_ROTEIRO", loja.id)
            startActivity(intent)
        }, this, loja)
    }
}