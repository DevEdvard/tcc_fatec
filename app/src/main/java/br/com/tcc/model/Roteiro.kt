package br.com.tcc.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Roteiro() : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var nomFantasia: String? = null
    var bandeira: String? = null
    var checkin: String? = null
    var checkout: String? = null
    var flProduto: Int? = null
    var flFotoExecucao: Int? = null

    var flColeta: Int? = null

    @Ignore
    constructor(nomFantasia: String,bandeira: String, checkin: String, checkout: String, flProduto: Int, flFotoExecucao: Int, flColeta: Int) : this(){
        this.nomFantasia = nomFantasia
        this.bandeira = bandeira
        this.checkin = checkin
        this.checkout = checkout
        this.flProduto = flProduto
        this.flFotoExecucao = flFotoExecucao
        this.flColeta = flColeta
    }

    constructor(id: Int, nomFantasia: String,bandeira: String, checkin: String, checkout: String, flProduto: Int, flFotoExecucao: Int, flColeta: Int) : this(){
        this.id = id
        this.nomFantasia = nomFantasia
        this.bandeira = bandeira
        this.checkin = checkin
        this.checkout = checkout
        this.flProduto = flProduto
        this.flFotoExecucao = flFotoExecucao
        this.flColeta = flColeta
    }

}