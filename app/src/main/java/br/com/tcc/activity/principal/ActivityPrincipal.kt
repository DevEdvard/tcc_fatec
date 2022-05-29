package br.com.tcc.activity.principal

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.tcc.R
import br.com.tcc.databinding.ActivityPrincipalBinding
import br.com.tcc.model.Usuario
import br.com.tcc.util.database.Database

class ActivityPrincipal : AppCompatActivity() {

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
        navController.enableOnBackPressed(false)

        _binding.bottomNavigation.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        _binding.toolbarPrincipal.setupWithNavController(navController, appBarConfiguration)
    }

}