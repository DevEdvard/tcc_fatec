package br.com.tcc.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DadosJson : Serializable {

    @SerializedName("MENSAGEM")
    var mMensagem: MensagemJson? = null
    @SerializedName("LOGIN")
    var mLogin: Usuario? = null
    @SerializedName("ROTEIRO")
    var mRoteiro = ArrayList<Roteiro>()
    @SerializedName("PRODUTO")
    var mProduto = ArrayList<Sku>()
    @SerializedName("MENSAGEMJSONRETORNO")
    var mMensagemEnvio: MensagemEnvio? = null

}