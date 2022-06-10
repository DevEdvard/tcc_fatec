package br.com.tcc.model

import android.content.Context
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Pesquisa : Serializable {

    @Ignore
    val PREFS_PESQUISA = "prefs_pesquisa"
    @Ignore
    val PREFS_PESQUISA_DADOS = "prefs_dados_pesquisa"

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @SerializedName("codPessoa")
    var codUsuario: Int? = null
    @SerializedName("codRoteiro")
    var codRoteiro: Int? = null
    @SerializedName("codJustificativa")
    var codJustificativa: Int? = null
    @SerializedName("desJustificativa")
    var desJustificativa: String? = null
    @SerializedName("coletaProduto")
    var coletaProduto: Int? = null
    @SerializedName("coletaFotoExecucao")
    var coletaFotoExecucao: Int? = null
    @SerializedName("checkin")
    var checkin: String? = null
    @SerializedName("checkout")
    var checkout: String? = null
    @SerializedName("transmissao")
    var transmissao: String? = null

    @Ignore
    @SerializedName("PRODUTO")
    var mLColetaProduto: ArrayList<ColetaProduto?>? = null

    /**
     * RETORAR LOGIN GRAVADO
     */

    fun retornar(context: Context): Pesquisa? {
        val prefs = context.getSharedPreferences(PREFS_PESQUISA, Context.MODE_PRIVATE)

        val json = prefs.getString(PREFS_PESQUISA_DADOS, "")
        val pesquisa = Gson().fromJson(json, Pesquisa::class.java)

        return pesquisa
    }

    @Throws(Exception::class)
    fun iniciar(context: Context, pesquisa: Pesquisa) {
        val prefs = context.getSharedPreferences(PREFS_PESQUISA, Context.MODE_PRIVATE)
        val json = prefs.getString(PREFS_PESQUISA_DADOS, "")
        val pesquisaJson = Gson().fromJson(json, Pesquisa::class.java)

        if (pesquisaJson != null)
            prefs.edit().clear().apply()

        val editor = prefs.edit()
        editor.putString(PREFS_PESQUISA_DADOS, Gson().toJson(pesquisa))
        editor.apply()

    }

    @Throws(Exception::class)
    fun deslogar(context: Context, redirecionar: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_PESQUISA, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

//        if (redirecionar)
//            SendIntent.with()
//                .mClassFrom(context as Activity)
//                .mClassTo(ActivityRoteiro::class.java)
//                .mType(R.integer.slide_from_left)
//                .go()
    }

    fun setmLColetaProdutoJson(mLColetaProdutoJson: ArrayList<ColetaProduto?>) {
        mLColetaProduto = mLColetaProdutoJson
    }

}