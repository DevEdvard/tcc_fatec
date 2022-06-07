package br.com.tcc.activity.principal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.tcc.controller.FragmentCompat
import br.com.tcc.databinding.FragmentInicialBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentInicial : FragmentCompat() {

    private lateinit var _binding: FragmentInicialBinding
    private val fabAtualizar: FloatingActionButton? = null

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
    
    private fun controles(){
        setupBtn()
    }

    private fun setupBtn() {
        val fabAtualizar = _binding.fabAtualizar
        fabAtualizar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            fabAtualizar -> Toast.makeText(requireContext(), "Teste 123", Toast.LENGTH_SHORT).show()
        }
    }
}