package br.com.tcc.activity.coleta

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tcc.R
import br.com.tcc.activity.coleta.fragment.JustificativaFragment
import br.com.tcc.activity.coleta.produto.ColetaProdutoActivity
import br.com.tcc.activity.principal.ActivityPrincipal
import br.com.tcc.activity.principal.fragment.FragmentRoteiro
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.ActivityMenuRoteiroBinding
import br.com.tcc.model.Pesquisa
import br.com.tcc.model.Roteiro
import br.com.tcc.model.Sku
import br.com.tcc.recycler.RecyclerMenuColeta
import br.com.tcc.util.Alerta
import br.com.tcc.util.SendIntent
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database
import br.com.tcc.util.database.dao.PesquisaDAOHelper
import br.com.tcc.util.database.dao.RoteiroDAOHelper
import br.com.tcc.util.webclient.tasks.TaskEnviarDados

class MenuRoteiroActivity : AppCompat() {

    private var recyclerMenu: RecyclerMenuColeta? = null
    private lateinit var _binding: ActivityMenuRoteiroBinding
    private lateinit var loja: Roteiro
    private var mPesquisa: Pesquisa? = null
    private var mContext = this

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
        recuperaPesquisa()
        configuraToolBar()
        iniciarRecycler()
        setarDadosColeta()
        setarBtnPesquisa()
    }

    private fun recuperaPesquisa() {
        val db = Database.getInstance(this)
        val dao = db.roomPesquisaDao
        mPesquisa = dao.selectRoteiro(loja.id)
    }

    private fun configuraToolBar() {
        setupToolbar(loja.nomFantasia)
    }

    private fun setarBtnPesquisa() {
        val db = Database.getInstance(this)
        val daoRoteiro = db.roomRoteiroDao
        val roteiro = daoRoteiro.selectId(loja.id!!)

        _binding.apply {

            imgJustificativa.setBackgroundResource(R.drawable.menu_justificativa)
            imgCheckout.setBackgroundResource(R.drawable.menu_checkout)
            imgTransmissao.setBackgroundResource(R.drawable.menu_transmissao)


            if (roteiro.flColeta != 1) {
                imgJustificativa.setBackgroundResource(R.drawable.menu_justificativa_ok)
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

        val dataSource = dao.selectId(loja.id!!)
        recyclerMenu!!.setDataSource(dataSource)
        db.close()
    }

    override fun onClick(v: View?) {
        val db = Database.getInstance(this)
        val daoRoteiro = db.roomRoteiroDao
        val daoSku = db.roomSkuDao
        val daoPesquisa = db.roomPesquisaDao

        val roteiro = daoRoteiro.selectId(loja.id!!)
        val validaColetaProduto = daoSku.selectModuloColetado(loja.codLoja!!)

        when (v) {
            _binding.imgJustificativa -> {
                validaJustificativa(roteiro)
            }

            _binding.imgCheckout -> {
                validaCheckout(roteiro, validaColetaProduto, daoRoteiro)
            }

            _binding.imgTransmissao -> {
                validaTransmissao(roteiro, daoRoteiro, daoPesquisa)
            }
        }
        db.close()
    }

    private fun validaTransmissao(
        roteiro: Roteiro,
        daoRoteiro: RoteiroDAOHelper,
        daoPesquisa: PesquisaDAOHelper,
    ) {
        if (roteiro.flColeta == 2) {
//            daoRoteiro.realizaTransmissao(roteiro.id!!, Util.dataHora())
            daoPesquisa.attTransmissao(mPesquisa!!.id!!, Util.dataHora())
            Alerta.show(
                this@MenuRoteiroActivity, resources.getString(R.string.msg_pesquisa),
                resources.getString(R.string.transmissao_msg_envio),
                resources.getString(R.string.sim),
                DialogInterface.OnClickListener { dialog, which ->
                    TaskEnviarDados(mContext, mPesquisa!!).execute()
                }, false
            )
        } else {
            Alerta.show(this, "Pesquisa em andamento",
                "A pesquisa ainda está em andamento, finalize a pesquisa para transmitir.",
                false)
        }
    }

    private fun validaCheckout(
        roteiro: Roteiro,
        validaColeta: List<Sku>,
        daoRoteiro: RoteiroDAOHelper,
    ) {
        if (roteiro.flColeta == 1) {
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
                            _binding.imgJustificativa.setBackgroundResource(R.drawable.menu_justificativa_ok)
                            dialog.dismiss()
                        })
                    .setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                alerta.create().show()
            }
        } else {
            val mensagem =
                if (roteiro.flJustificada == 1) "A loja já foi justificada." else "Já foi realizado o Check-out."
            aviso("Justificativa", mensagem)
        }
    }

    private fun validaJustificativa(roteiro: Roteiro) {
        if (roteiro.flColeta == 1) {
            val fragmentJus = JustificativaFragment(loja.id!!)
            fragmentJus.show(supportFragmentManager, "justificativaDialog")
        } else {
            val mensagem =
                if (roteiro.flJustificada == 1) "A loja já foi justificada." else "Já foi realizado o Check-out."
            aviso("Justificativa", mensagem)
        }
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
        val daoColetaProduto = db.roomColetaProdutoDao
        val roteiro = daoRoteiro.selectId(loja.id!!)
        val pesquisaOld = daoPesquisa.selectRoteiro(loja.id!!)

        val seTemPesquisa = daoPesquisa.selectRoteiro(loja.id)

        if (seTemPesquisa != null) {
            val mPesquisa = Pesquisa()

            mPesquisa.apply {
                codUsuario = daoUsuario.select()!!.id
                codRoteiro = loja.id
                codJustificativa = 0
                desJustificativa = ""
                coletaProduto = roteiro.flProduto
                coletaFotoExecucao = roteiro.flFotoExecucao
                checkin = roteiro.checkin
                checkout = roteiro.checkout
                transmissao = ""
            }

            daoPesquisa.deleteId(loja.id!!)
            daoPesquisa.insert(mPesquisa)
            val pesquisaNew = daoPesquisa.selectRoteiro(loja.id)
            daoColetaProduto.updateCodPesquisa(pesquisaOld!!.id!!, pesquisaNew!!.id!!)
        }
        db.close()
    }

    private fun setOnItemClick() {
        recyclerMenu = RecyclerMenuColeta({ itemClicked ->
            val intent = Intent(this@MenuRoteiroActivity, ColetaProdutoActivity::class.java)
            intent.putExtra("LOJA_ROTEIRO", loja.id)
            startActivity(intent)
        }, this, loja)
    }

    override fun onRetorno(aBoolean: Boolean, mensagem: String) {
        if(aBoolean) {
            val db = Database.getInstance(this)
            val dao = db.roomRoteiroDao

            dao.realizaTransmissao(mPesquisa!!.codRoteiro!!, Util.dataHora())

            val AlertDialog = AlertDialog
                .Builder(this)
                .setTitle("Transmissão concluída")
                .setMessage("Loja transmitida com sucesso!")
                .setNegativeButton("Ok",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                        voltar() })
            AlertDialog.create().show()
        } else {
            val AlertDialog = AlertDialog
                .Builder(this)
                .setTitle("Erro")
                .setMessage(mensagem)
                .setNegativeButton("Ok",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            AlertDialog.create().show()
        }
    }

    private fun voltar() {
        SendIntent.with()
            .mClassFrom(this@MenuRoteiroActivity)
            .mClassTo(ActivityPrincipal::class.java)
            .mType(R.integer.slide_from_left)
            .go()
    }
}