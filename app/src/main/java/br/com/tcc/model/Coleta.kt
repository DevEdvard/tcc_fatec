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

    @Ignore
    var coleta_ok : Boolean? = false

}