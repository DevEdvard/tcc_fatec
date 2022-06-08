package br.com.tcc.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Roteiro : Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("codRoteiro")
    var id: Int? = null
    @SerializedName("codLoja")
    var codLoja: Int? = null
    @SerializedName("nomFantasia")
    var nomFantasia: String? = null
    @SerializedName("bandeira")
    var bandeira: String? = null
    var checkin: String = ""
    var checkout: String = ""
    @SerializedName("flProduto")
    var flProduto: Int? = null
    @SerializedName("flFotoExecucao")
    var flFotoExecucao: Int? = null

    var flColeta: Int = 0
    var flJustificada: Int = 0

}