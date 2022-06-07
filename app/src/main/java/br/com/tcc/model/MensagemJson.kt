package br.com.tcc.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MensagemJson : Serializable {
    @SerializedName("status")
    var status : Int? = null
    @SerializedName("descricao")
    var mensagem : String? = null
}