package br.com.tcc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Sku : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var desc: String? = null
    var marca: String? = null
    var precoMax: Float? = null
    var precoMin: Float? = null
    var vinculoLoja: Int? = null
    var flEstoque: Int? = null

    var flColetado: Int? = 0

}