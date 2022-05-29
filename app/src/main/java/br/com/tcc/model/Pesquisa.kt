package br.com.tcc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Pesquisa : Serializable {

    @PrimaryKey(autoGenerate = true)
    private var id: Int? = null
    private var codPessoa: Int? = null
    private var codLoja: Int? = null
    private var coletaProduto: Int? = null
    private var coletaFotoExecucao: Int? = null
    private var checkin: String? = null
    private var checkout: String? = null
    private var transmissao: String? = null

}