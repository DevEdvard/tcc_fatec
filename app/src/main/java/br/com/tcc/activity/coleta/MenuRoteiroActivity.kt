package br.com.tcc.activity.coleta

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tcc.R
import br.com.tcc.activity.coleta.produto.ColetaProdutoActivity
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.ActivityMenuRoteiroBinding
import br.com.tcc.model.Roteiro
import br.com.tcc.recycler.RecyclerMenuColeta
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

    override fun onShow(dialog: DialogInterface?) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        setarBtnPesquisa()
    }

    private fun controles() {
        loja = getExtras()
        setupToolbar(loja.nomFantasia)
        iniciarRecycler()
        setarDadosColeta()
        setarBtnPesquisa()
    }

    private fun setarBtnPesquisa() {
        val db = Database.getInstance(this)
        val dao = db.roomRoteiroDao
        val roteiro = dao.select(loja.id!!)

        _binding.apply {

            imgJustificativa.setOnClickListener(this@MenuRoteiroActivity)
            if (roteiro.flColeta == 1) {
                imgCheckout.setBackgroundResource(R.drawable.menu_checkout)
                imgCheckout.setOnClickListener(this@MenuRoteiroActivity)

            } else {
                imgCheckout.setBackgroundResource(R.drawable.menu_checkout_ok)
                imgCheckout.setOnClickListener(this@MenuRoteiroActivity)
            }
            imgTransmissao.setOnClickListener(this@MenuRoteiroActivity)
        }


    }

    private fun getExtras(): Roteiro {
        val extra = intent.getIntExtra("LOJA", 0)
        val db = Database.getInstance(this)
        val dao = db.roomRoteiroDao
        val roteiro = dao.selectLojaRoteiro(extra)
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
        when (v) {
            _binding.imgJustificativa -> Toast.makeText(this, "Clicou mesmo 1", Toast.LENGTH_SHORT)
                .show()

            _binding.imgCheckout -> {
                val db = Database.getInstance(this)
                val daoRoteiro = db.roomRoteiroDao
                val daoSku = db.roomSkuDao

                val validaCheckout = daoRoteiro.select(loja.id!!)
                val validaColeta = daoSku.selectModuloColetado(loja.id!!)

                if (validaCheckout.flColeta == 1) {
                    if (!validaColeta.isEmpty()) {
                        val alerta = AlertDialog
                            .Builder(this)
                            .setTitle("Check-out")
                            .setMessage("A pesquisa ainda não foi realizada.")
                            .setNeutralButton("Ok",
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                })

                        alerta.create().show()
                        db.close()
                    } else {
                        val alerta = AlertDialog
                            .Builder(this)
                            .setTitle("Check-out")
                            .setMessage("Tem certeza que deseja realizar o check-out?")
                            .setPositiveButton("Confirmar",
                                DialogInterface.OnClickListener { dialog, which ->
                                    daoRoteiro.realizaCheckout(loja.id!!)
                                    _binding.imgCheckout.setBackgroundResource(R.drawable.menu_checkout_ok)
                                    dialog.dismiss()
                                })
                            .setNegativeButton("Cancelar",
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                })

                        alerta.create().show()
                        db.close()
                    }
                } else {
                    val alerta = AlertDialog
                        .Builder(this)
                        .setTitle("Check-out")
                        .setMessage("O check-out já foi realizado.")
                        .setNeutralButton("Ok",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })

                    alerta.create().show()
                }
            }
            _binding.imgTransmissao -> Toast.makeText(this, "Clicou mesmo 3", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setOnItemClick() {
        recyclerMenu = RecyclerMenuColeta({ itemClicked ->
            val intent = Intent(this@MenuRoteiroActivity, ColetaProdutoActivity::class.java)
            intent.putExtra("LOJA_ROTEIRO", loja.id)
            startActivity(intent)
        }, this, loja)
    }
}