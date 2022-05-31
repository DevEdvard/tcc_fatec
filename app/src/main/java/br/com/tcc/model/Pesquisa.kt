package br.com.tcc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Pesquisa : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var codUsuario: Int? = null
    var codLoja: Int? = null
    var codJustificativa: Int? = null
    var desJustificativa: String? = null
    var coletaProduto: Int? = null
    var coletaFotoExecucao: Int? = null
    var checkin: String? = null
    var checkout: String? = null
    var transmissao: String? = null

}