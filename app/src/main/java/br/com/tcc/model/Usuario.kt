package br.com.tcc.model

import android.app.Activity
import android.content.Context
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import br.com.tcc.R
import br.com.tcc.activity.principal.ActivityPrincipal
import br.com.tcc.util.SendIntent
import com.google.gson.Gson
import java.io.Serializable

@Entity
class Usuario() : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var nome: String? = null
    var login: String? = null
    var senha: String? = null
    var perfil: String? = null

    @Ignore
    private var PREFS_LOGIN: String = "prefs_usuario";
    @Ignore
    private var PREFS_LOGIN_DADOS: String = "prefs_usuario_dados";

    constructor(id: Int, nome: String, login: String, senha: String, perfil: String) : this() {
        this.id = id
        this.nome = nome
        this.login = login
        this.senha = senha
        this.perfil = perfil
    }

    constructor(nome: String, login: String, senha: String) : this() {
        this.nome = nome
        this.login = login
        this.senha = senha
    }

    @Throws(Exception::class)
    fun iniciar(context: Context, login: Usuario, direcionar: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_LOGIN, Context.MODE_PRIVATE)
        val json = prefs.getString(PREFS_LOGIN_DADOS, "")
        val loginJson = Gson().fromJson(json, Usuario::class.java)

        if (loginJson != null)
            prefs.edit().clear().apply()

        val editor = prefs.edit()
        editor.putString(PREFS_LOGIN_DADOS, Gson().toJson(login))
        editor.apply()

        if (direcionar)
            SendIntent.with()
                .mClassFrom(context as Activity)
                .mClassTo(ActivityPrincipal::class.java)
                .mType(R.integer.slide_from_right)
                .go()
    }
}