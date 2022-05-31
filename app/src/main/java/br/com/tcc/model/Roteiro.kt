package br.com.tcc.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Roteiro : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var codLoja: Int? = null
    var nomFantasia: String? = null
    var bandeira: String? = null
    var checkin: String? = null
    var checkout: String? = null
    var flProduto: Int? = null
    var flFotoExecucao: Int? = null

    var flColeta: Int? = null

}