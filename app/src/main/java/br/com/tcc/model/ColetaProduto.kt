package br.com.tcc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class ColetaProduto : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var idProduto: Int? = null
    var desProduto: String? = null
    var desMarca: String? = null
    var flPresente: Int? = null
    var preco: String? = null
    var frentes: Int? = null
    var estoque: Int? = null

}