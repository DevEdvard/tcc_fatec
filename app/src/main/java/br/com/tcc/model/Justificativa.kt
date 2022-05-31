package br.com.tcc.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Justificativa() : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
    var descricao : String? = null

    @Ignore
    constructor(id: Int, desc: String) : this() {
        this.id = id
        this.descricao = desc
    }
}