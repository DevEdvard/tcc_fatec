package br.com.tcc.activity.principal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import br.com.tcc.databinding.FragmentInicialBinding

class FragmentInicial : Fragment() {

    private lateinit var _biding: FragmentInicialBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _biding = FragmentInicialBinding.inflate(inflater)
        return _biding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}