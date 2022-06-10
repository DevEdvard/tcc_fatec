package br.com.tcc.activity.principal.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tcc.R
import br.com.tcc.controller.FragmentCompat
import br.com.tcc.databinding.FragmentInicialBinding
import br.com.tcc.model.Usuario
import br.com.tcc.util.database.Database
import br.com.tcc.util.webclient.tasks.TaskRoteiro
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentInicial : FragmentCompat() {

    private lateinit var _binding: FragmentInicialBinding
    private var fabAtualizar: FloatingActionButton? = null
    private var mLogin: Usuario? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInicialBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controles()
    }

    private fun controles() {
        setupBtn()
    }

    private fun setupBtn() {
        fabAtualizar = _binding.fabAtualizar
        fabAtualizar!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            fabAtualizar -> {
                val db = Database.getInstance(requireContext())
                val dao = db.roomRoteiroDao
                val listaRoteiro = dao.selectListaRoteiro()
                mLogin = Usuario().retornar(requireContext()) as Usuario

                if (!listaRoteiro.isEmpty()) {
                    val alerta = AlertDialog
                        .Builder(requireContext())
                        .setTitle(resources.getString(R.string.msg_atencao))
                        .setMessage(resources.getString(R.string.roteiro_atualizado_sincronizacao))
                        .setPositiveButton("Confirmar",
                            DialogInterface.OnClickListener { dialog, which ->
                                TaskRoteiro(requireContext()).execute(mLogin!!.login.toString(),
                                    mLogin!!.senha.toString())
                            })
                        .setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })
                    db.close()
                    alerta.create().show()
                } else {
                    TaskRoteiro(requireContext()).execute(mLogin!!.login.toString(),
                        mLogin!!.senha.toString())
                }
            }
        }
    }
}