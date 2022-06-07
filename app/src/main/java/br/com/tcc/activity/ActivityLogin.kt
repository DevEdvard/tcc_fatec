package br.com.tcc.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import br.com.tcc.R
import br.com.tcc.activity.principal.ActivityPrincipal
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.ActivityLoginBinding
import br.com.tcc.model.Usuario
import br.com.tcc.util.Alerta
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database
import br.com.tcc.util.webclient.tasks.TaskLogin


val usuarioTeste = Usuario(1, "User Tester", "teste", "1234", "Promotor", "")

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

    private fun validaUsuario(usuario: Usuario, usuarioTeste: Usuario) {
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

    private fun validaUsuario2() {

//        if (!CheckReadPermission.validaPermissao(this)) {
//            if (Build.VERSION.SDK_INT >= 23)
//                CheckReadPermission.show(this@ActivityLogin)
//
//            return
//        }

        if (!Util.validaEditText(_binding.loginEdtUsuario, this@ActivityLogin)
            || !Util.validaEditText(_binding.loginEdtSenha, this@ActivityLogin)
        )
            return

        val db = Database.getInstance(this)
        val dao = db.roomUsuarioDao

        val usuario = _binding.loginEdtUsuario.text.toString()
        val senha = _binding.loginEdtSenha.text.toString()

        val login = dao.selectUsuarioNome(usuario)
        db.close()

        if (login != null)
            Usuario().iniciar(this@ActivityLogin, login, true)
        else
            TaskLogin(this@ActivityLogin).execute(usuario, senha)
    }

    override fun onClick(v: View?) {
        when (v) {
            _binding.loginBtnEntrar -> {
//                validaUsuario(criaUsuarioTeste(), usuarioTeste)
                validaUsuario2()
            }
        }
    }

    override fun onRetorno(aBoolean: Boolean, mensagem: String) {
        try {
            if (aBoolean) {

                val db = Database.getInstance(this)
                val dao = db.roomUsuarioDao

                val usuario = _binding.loginEdtUsuario.text.toString()
                val senha = _binding.loginEdtSenha.text.toString()

                val login = dao.select()
                db.close()

                Usuario().iniciar(this@ActivityLogin, login, true)

            } else
                Alerta.show(
                    this@ActivityLogin,
                    resources.getString(R.string.msg_atencao),
                    Html.fromHtml(mensagem).toString(),
                    true
                )

        } catch (err: Exception) {
            err.toString()
        }
    }
}
