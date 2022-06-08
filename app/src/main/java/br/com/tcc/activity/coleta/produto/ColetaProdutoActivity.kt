package br.com.tcc.activity.coleta.produto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tcc.activity.coleta.produto.fragment.ColetaProdutoFragment
import br.com.tcc.databinding.ActivityColetaProdutoBinding
import br.com.tcc.model.Roteiro
import br.com.tcc.recycler.RecyclerColetaProduto
import br.com.tcc.util.database.Database

class ColetaProdutoActivity : AppCompatActivity() {

    private var recyclerColetaProduto: RecyclerColetaProduto? = null
    private lateinit var _binding: ActivityColetaProdutoBinding
    private lateinit var loja: Roteiro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityColetaProdutoBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        controles()
    }

    private fun controles() {
        loja = getExtras()
        setupToolbar(loja.nomFantasia)
        iniciarRecycler()
        setarDadosColeta()
    }

    private fun getExtras(): Roteiro {
        val extra = intent.getIntExtra("LOJA_ROTEIRO", 0)
        val db = Database.getInstance(this)
        val dao = db.roomRoteiroDao
        val roteiro = dao.selectId(extra)
        db.close()
        return roteiro
    }

    private fun setupToolbar(title: String?) {
        _binding.toolbarColetaProduto.title = title
    }

    private fun iniciarRecycler() {
        setOnItemClick()
        _binding.recyclerViewColetaProduto.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = recyclerColetaProduto
        }
    }

    private fun setarDadosColeta() {
        val db = Database.getInstance(this)
        val dao = db.roomSkuDao

        val dataSource = dao.selectListaSkuColeta(loja.codLoja!!)
        recyclerColetaProduto!!.setDataSource(dataSource)
        db.close()

    }

    private fun setOnItemClick() {
        recyclerColetaProduto = RecyclerColetaProduto { itemClicked ->

            if (itemClicked.flColetado == 1) {

                val db = Database.getInstance(this)
                val dao = db.roomColetaProdutoDao

                itemClicked.id?.let {
                    val coletaProduto = dao.selectColetaProduto(it)
                    val dialog = ColetaProdutoFragment(coletaProduto.idProduto!!)
                    dialog.show(supportFragmentManager, "coletaProdutoDialog")
                    db.close()
                }
            } else {
                val dialog = ColetaProdutoFragment(itemClicked.id!!)
                dialog.show(supportFragmentManager, "coletaProdutoDialog")
            }
        }
    }
}