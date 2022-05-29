package br.com.tcc.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Coleta : Serializable {

    @PrimaryKey(autoGenerate = false)
    var id: Int? = null
    var desColeta : String? = null
    var foto: String? = null
    var fotoOK: String? = null
    var staColeta : Int? = null

    @Ignore
    constructor(id: Int, desc: String, status: Int) : this() {
        this.id = id
        this.desColeta = desc
        this.staColeta = status
    }

    constructor()

}