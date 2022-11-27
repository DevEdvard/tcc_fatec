package br.com.tcc.activity.coleta.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import br.com.tcc.R
import br.com.tcc.activity.coleta.MenuRoteiroActivity
import br.com.tcc.adapter.AdapterJustificativa
import br.com.tcc.controller.CallBack_Projeto
import br.com.tcc.controller.FragmentCompat
import br.com.tcc.databinding.FragmentJustificativaBinding
import br.com.tcc.model.Justificativa
import br.com.tcc.model.Pesquisa
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database

class JustificativaFragment(id: Int?) : FragmentCompat(), AdapterView.OnItemSelectedListener {

    private lateinit var _binding: FragmentJustificativaBinding
    private lateinit var mAlertDialog: AlertDialog
    private lateinit var btnSalvar: Button
    private lateinit var btnFechar: Button
    private lateinit var mSpnJustificativa: Spinner
    private lateinit var mJustificativa: Justificativa
    private var idLoja = id

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentJustificativaBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(context)
            .setIcon(null)
            .setTitle(R.string.jutificativa)
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
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        controles()
    }

    private fun controles() {
        configuraSpiner()
    }

    private fun configuraSpiner() {
        _binding.apply {
            val db = Database.getInstance(requireContext())
            val dao = db.roomJusitivicativaDao
            val list: ArrayList<Justificativa> = ArrayList()
            list.add(Justificativa(0,"SELECIONE"))
            list.addAll(dao.select())

            if (list.isEmpty()) {
                dao.insertTeste()
            }

            spnJustificativa.adapter = AdapterJustificativa(requireContext(),list)

            db.close()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnSalvar -> gravar()
            btnFechar -> this.dialog!!.dismiss()
        }
    }

    override fun onShow(dialog: DialogInterface?) {
        btnSalvar = mAlertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
        btnSalvar.setOnClickListener(this)
        btnFechar = mAlertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
        btnFechar.setOnClickListener(this)
        mSpnJustificativa = _binding.spnJustificativa
        mSpnJustificativa.onItemSelectedListener = this
    }

    private fun gravar() {

        mJustificativa = mSpnJustificativa.selectedItem as Justificativa

        if(!Util.validaSpinnerJustificativa(mSpnJustificativa, requireContext()))
            return

        if(!Util.validaEditText(_binding.edtJustificativa, requireContext()))
            return

        val db = Database.getInstance(this.context)
        val daoPesquisa = db.roomPesquisaDao
        val daoUsuario = db.roomUsuarioDao
        val daoRoteiro = db.roomRoteiroDao

        val usuario = daoUsuario.select()

        daoPesquisa.deleteId(idLoja!!)
        val mPesquisa = Pesquisa()

        _binding.apply {
            mPesquisa.codUsuario = usuario!!.id
            mPesquisa.codRoteiro = idLoja
            mPesquisa.codJustificativa = mJustificativa.id
            mPesquisa.desJustificativa = mJustificativa.descricao
            mPesquisa.coletaProduto = 0
            mPesquisa.coletaFotoExecucao = 0
            mPesquisa.checkin = ""
            mPesquisa.checkout = ""
            mPesquisa.transmissao = ""
            daoPesquisa.insert(mPesquisa)
            daoRoteiro.justifica(idLoja!!, Util.dataHora())
            db.close()

            val context = requireContext() as MenuRoteiroActivity
            val callback = context as CallBack_Projeto
            callback.onRetorno(true)

            this@JustificativaFragment.dialog!!.dismiss()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mJustificativa = parent!!.getItemAtPosition(position) as Justificativa
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}