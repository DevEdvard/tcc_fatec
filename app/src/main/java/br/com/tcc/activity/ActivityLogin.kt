package br.com.tcc.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.tcc.activity.principal.ActivityPrincipal
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.ActivityLoginBinding
import br.com.tcc.model.Usuario
import br.com.tcc.util.database.Database


val usuarioTeste = Usuario(1, "User Tester", "teste", "1234", "Promotor")

class ActivityLogin : AppCompat() {

    private lateinit var _binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        controles()

        val db: Database = Database.getInstance(this)
        val dao = db.getRoomUsuarioDao()

        dao.deleteAll()
        dao.insert(usuarioTeste)
        db.close()
    }

    override fun onShow(dialog: DialogInterface?) {
        TODO("Not yet implemented")
    }

    private fun criaUsuarioTeste(): Usuario {
        val database: Database = Database.getInstance(this)
        val dao = database.getRoomUsuarioDao()

        _binding.apply {

            val pTeste = Usuario(
                "teste",
                loginEdtUsuario.getText().toString(),
                loginEdtSenha.getText().toString())

            val retorno = dao.selectUsuarioNome(pTeste.login!!)

            if (retorno != null) {
                return retorno
            } else {
                return Usuario("null", "null", "null")
            }

        }

    }

    fun controles() {
        _binding.loginBtnEntrar.setOnClickListener(this)
    }

    private fun validaUsuário(usuario: Usuario, usuarioTeste: Usuario) {
        if (!usuario.login.isNullOrEmpty() || !usuario.senha.isNullOrEmpty()) {
            if (usuario.login == usuarioTeste.login && usuario.senha == usuarioTeste.senha) {
                Toast.makeText(this, "Login Efetuado!", Toast.LENGTH_SHORT).show()
                val telaPrincipal = Intent(this, ActivityPrincipal::class.java)
                telaPrincipal.putExtra("usuario", usuario)
                startActivity(telaPrincipal)
            } else {
                Toast.makeText(this, "Usuário inválido", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencher os dados para efetuar o login", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            _binding.loginBtnEntrar -> {
                validaUsuário(criaUsuarioTeste(), usuarioTeste)
            }
        }
    }
}
