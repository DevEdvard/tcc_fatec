package br.com.tcc.activity.principal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.tcc.databinding.FragmentPerfilBinding
import br.com.tcc.model.Usuario
import br.com.tcc.util.database.Database

class FragmentPerfil : Fragment() {

    private lateinit var _biding: FragmentPerfilBinding
    private lateinit var usuario: Usuario

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _biding = FragmentPerfilBinding.inflate(inflater)
        return _biding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = Database.getInstance(requireContext())
        val dao = db.roomUsuarioDao

        val usuarioSelected = dao.select()

        if (usuarioSelected == null) {
            dao.insertTeste()
            usuario = dao.select()!!
        } else {
            usuario = usuarioSelected
        }
        db.close()

        _biding.apply {
            txtNomeUsuario.text = usuario.nome
            txtPerfilUsuario.text = usuario.perfil
        }
    }


}