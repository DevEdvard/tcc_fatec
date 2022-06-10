package br.com.tcc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class ColetaProduto : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @SerializedName("codPesquisa")
    var codPesquisa: Int? = null
    @SerializedName("codProduto")
    var idProduto: Int? = null
    @SerializedName("desProduto")
    var desProduto: String? = null
    @SerializedName("desMarca")
    var desMarca: String? = null
    @SerializedName("flPresente")
    var flPresente: Int? = null
    @SerializedName("preco")
    var preco: String? = null
    @SerializedName("frentes")
    var frentes: Int? = null
    @SerializedName("estoque")
    var estoque: Int? = null

}