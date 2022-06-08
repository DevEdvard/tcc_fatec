package br.com.tcc.activity.principal.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tcc.activity.coleta.MenuRoteiroActivity
import br.com.tcc.databinding.FragmentRoteiroBinding
import br.com.tcc.model.Pesquisa
import br.com.tcc.recycler.RecyclerRoteiro
import br.com.tcc.util.database.Database

class FragmentRoteiro() : Fragment() {

    private var recycler: RecyclerRoteiro? = null
    private lateinit var _binding: FragmentRoteiroBinding
    private lateinit var mPesquisa: Pesquisa

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRoteiroBinding.inflate(inflater)
        return _binding.root
    }

    override fun onResume() {
        super.onResume()
        controles()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding
    }

    private fun controles() {
        iniciarRecycler()
        setarDados()

        val db = Database.getInstance(requireContext())
        val dao = db.roomSkuDao
        val listaSkus = dao.selectAll()

//        if (listaSkus.isEmpty()) {
//            dao.insertSkuTeste()
//        }
        db.close()
    }

    private fun setarDados() {
        val database = Database.getInstance(this.context)
        val db = database.roomRoteiroDao
        val dataSource = db.selectListaRoteiro()
        this.recycler!!.setDataSource(dataSource)
        database.close()
    }

    private fun iniciarRecycler() {
        setOnItemClick()
        _binding.recyclerViewRoteiro.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = recycler
        }
    }

    private fun setOnItemClick() {
        this.recycler = RecyclerRoteiro({ itemClicked ->
            val intent = Intent(this@FragmentRoteiro.context, MenuRoteiroActivity::class.java)
            intent.putExtra("ROTEIRO", itemClicked.id)
            startActivity(intent)
        }, requireContext())
    }
}