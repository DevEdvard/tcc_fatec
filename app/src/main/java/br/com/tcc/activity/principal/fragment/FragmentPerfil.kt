package br.com.tcc.activity.principal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import br.com.tcc.controller.FragmentCompat
import br.com.tcc.databinding.FragmentPerfilBinding
import br.com.tcc.model.Usuario
import br.com.tcc.util.CarregaImagem

class FragmentPerfil : FragmentCompat() {

    private lateinit var _binding: FragmentPerfilBinding
    private var btnDeslogar: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controles()
        val sessao = Usuario().retornar(requireContext())

        _binding.apply {
            txtNomeUsuario.text = sessao!!.nome
            txtPerfilUsuario.text = sessao.perfil
        }
        if(sessao!!.fotoPerfil != null) {
            CarregaImagem.donwload(_binding.imageView2, sessao.fotoPerfil!!, 1)
        }
    }

    private fun controles() {
        _binding.btnLogout.setOnClickListener(this)
        btnDeslogar = _binding.btnLogout
    }

    override fun onClick(v: View?) {
        when(v){
            btnDeslogar -> Usuario().deslogar(requireContext(), true)
        }
    }
}