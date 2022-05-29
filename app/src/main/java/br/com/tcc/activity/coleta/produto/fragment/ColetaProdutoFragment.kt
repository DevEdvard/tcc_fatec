package br.com.tcc.activity.coleta.produto.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import br.com.tcc.R
import br.com.tcc.controller.FragmentCompat
import br.com.tcc.databinding.FragmentColetaProdutoBinding
import br.com.tcc.model.ColetaProduto
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database


class ColetaProdutoFragment(id: Int?) : FragmentCompat() {

    private var mColetaProduto: ColetaProduto? = null
    private lateinit var _binding: FragmentColetaProdutoBinding
    private lateinit var mAlertDialog: AlertDialog
    private lateinit var btnSalvar: Button
    private lateinit var btnFechar: Button
    private var idProduto = id

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentColetaProdutoBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(context)
            .setIcon(null)
            .setTitle(R.string.coleta_produto)
            .setPositiveButton(R.string.gravar, null)
            .setNegativeButton(R.string.cancelar, null)

        mAlertDialog = builder.setView(_binding.root).create()
        mAlertDialog.setOnShowListener(this)

        return mAlertDialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        controles()
    }

    private fun controles() {
        setTitleDialog()
        mColetaProduto = recuperaProduto()
        recuperaDadosProduto()
        _binding.apply {
            sProdutoPresente.setOnClickListener(this@ColetaProdutoFragment)
        }
    }

    private fun setTitleDialog() {
        val db = Database.getInstance(this.context)
        val daoSku = db.roomSkuDao
        val produto = daoSku.select(idProduto!!)
        _binding.txtTitle.text = produto.desc
        db.close()
    }

    override fun onClick(v: View?) {
        when (v) {
            _binding.sProdutoPresente ->
                _binding.apply {
                    if (sProdutoPresente.isChecked) {
                        lnPreco.visibility = View.VISIBLE
                        lnFrentes.visibility = View.VISIBLE
                        lnEstoque.visibility = View.VISIBLE
                    } else if (!sProdutoPresente.isChecked) {
                        lnPreco.visibility = View.GONE
                        lnFrentes.visibility = View.GONE
                        lnEstoque.visibility = View.GONE
                    }
                }

            btnSalvar -> gravar()

            btnFechar -> this.dialog!!.dismiss()
        }
    }

    override fun onShow(dialog: DialogInterface?) {
        btnSalvar = mAlertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
        btnSalvar.setOnClickListener(this)
        btnFechar = mAlertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
        btnFechar.setOnClickListener(this)
    }

    private fun gravar() {
        if (validarCampos(_binding.sProdutoPresente.isChecked) == false)
            return

        val db = Database.getInstance(this.context)
        val daoColetaProduto = db.roomColetaProdutoDao
        val daoSku = db.roomSkuDao
        daoColetaProduto.deleteId(idProduto)
        val mColeta = ColetaProduto()

        _binding.apply {
            mColeta.idProduto = idProduto
            mColeta.desProduto = daoSku.select(idProduto!!).desc
            mColeta.desMarca = daoSku.select(idProduto!!).marca
            mColeta.flPresente = if (sProdutoPresente.isChecked) 1 else 0
            mColeta.preco = if (sProdutoPresente.isChecked) edtPreco.text.toString() else ""
            mColeta.frentes =
                if (sProdutoPresente.isChecked) edtFrentes.text.toString().toInt() else 0
            mColeta.estoque =
                if (sProdutoPresente.isChecked) edtEstoque.text.toString().toInt() else 0
            daoSku.updateColetado(idProduto!!)
            daoColetaProduto.insert(mColeta)
            db.close()
            this@ColetaProdutoFragment.dialog!!.dismiss()
        }
    }


    @SuppressLint("UseRequireInsteadOfGet")
    private fun validarCampos(presente: Boolean): Boolean {
        var validado = true

        if (presente) {
            _binding.apply {
                Util.apply {
                    if (_binding.lnPreco.visibility == View.VISIBLE && !validaEditText(edtPreco,
                            context!!)
                    )
                        validado = false
                    if (_binding.lnFrentes.visibility == View.VISIBLE && !validaEditTextInteiro(
                            edtFrentes,
                            context!!,
                            true)
                    )
                        validado = false
                    if (_binding.lnEstoque.visibility == View.VISIBLE && !validaEditTextInteiro(
                            edtEstoque,
                            context!!,
                            true)
                    )
                        validado = false
                }
            }
        }
        return validado
    }

    private fun recuperaDadosProduto() {
        if (mColetaProduto != null) {
            _binding.apply {
                sProdutoPresente.isChecked =
                    if (mColetaProduto!!.flPresente == 0) false else true
                if (sProdutoPresente.isChecked) {
                    edtPreco.setText(mColetaProduto!!.preco)
                    edtFrentes.setText(mColetaProduto!!.frentes.toString())
                    edtEstoque.setText(mColetaProduto!!.estoque.toString())
                    txtTitle.setText(mColetaProduto!!.desProduto)
                    lnPreco.visibility = View.VISIBLE
                    lnFrentes.visibility = View.VISIBLE
                    lnEstoque.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun recuperaProduto(): ColetaProduto? {
        val db = Database.getInstance(this.context)
        val daoSku = db.roomSkuDao
        var mProdutoColetado: ColetaProduto? = null

        val produto = daoSku.select(idProduto!!)
        if (produto.flColetado == 1) {
            val daoColetaProduto = db.roomColetaProdutoDao
            mProdutoColetado = daoColetaProduto.selectColetaProduto(produto.id!!)
            db.close()
            return mProdutoColetado
        }
        return mProdutoColetado
    }


}