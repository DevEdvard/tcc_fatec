package br.com.tcc.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import br.com.tcc.R
import br.com.tcc.activity.principal.ActivityPrincipal
import br.com.tcc.controller.AppCompat
import br.com.tcc.databinding.ActivityLoginBinding
import br.com.tcc.model.Usuario
import br.com.tcc.util.Alerta
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database
import br.com.tcc.util.webclient.tasks.TaskLogin

class ActivityLogin : AppCompat() {

    private lateinit var _binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        controles()
    }

    fun controles() {
        validaSessao()
        _binding.loginBtnEntrar.setOnClickListener(this)
    }

    private fun validaSessao() {
        val sessao = Usuario().retornar(this)
        if (sessao != null) {
            val intent = Intent(this, ActivityPrincipal::class.java)
            startActivity(intent)
        }
    }

    private fun validaLogin() {

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
            _binding.loginBtnEntrar -> validaLogin()
        }
    }

    override fun onRetorno(aBoolean: Boolean, mensagem: String) {
        try {
            if (aBoolean) {

                val db = Database.getInstance(this)
                val dao = db.roomUsuarioDao
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
