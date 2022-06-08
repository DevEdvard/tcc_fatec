package br.com.tcc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Sku : Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("codSku")
    var id: Int? = null
    @SerializedName("desSku")
    var desc: String? = null
    @SerializedName("desMarca")
    var marca: String? = null
    @SerializedName("precoMax")
    var precoMax: String? = null
    @SerializedName("precoMin")
    var precoMin: String? = null
    @SerializedName("codLoja")
    var codLoja: Int? = null
    @SerializedName("flValidade")
    var flValidade: Int? = null
    @SerializedName("flEstoque")
    var flEstoque: Int? = null

    var flColetado: Int? = 0

}