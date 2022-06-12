package br.com.tcc.activity.principal

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.tcc.R
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.ActivityPrincipalBinding
import br.com.tcc.model.Usuario

class ActivityPrincipal : AppCompat() {

    private lateinit var _binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        controles()
    }

    private fun controles() {

        setupToolbar()

        val dadosRecebidos = intent
        if (dadosRecebidos.hasExtra("usuario")) {
            val usuarioRecebido = dadosRecebidos.getSerializableExtra("usuario") as Usuario?
            val textViewPrincipal = findViewById<TextView>(R.id.home_textview_principal)
            textViewPrincipal.text = usuarioRecebido!!.nome
        }

    }

    private fun setupToolbar() {
        val navHostFragment =
            (supportFragmentManager.findFragmentById(_binding.fragmentContainerView.id)) as NavHostFragment
        val navController = navHostFragment.navController

        _binding.bottomNavigation.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        _binding.toolbarPrincipal.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onRetorno(aBoolean: Boolean, mensagem: String) {
        if(aBoolean) {
            val AlertDialog = AlertDialog
                .Builder(this)
                .setTitle("Dados atualizados")
                .setMessage("Os dados foram atualizados com sucesso!")
                .setNegativeButton("Ok",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
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
}