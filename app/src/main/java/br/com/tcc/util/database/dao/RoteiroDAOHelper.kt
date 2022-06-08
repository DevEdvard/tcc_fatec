package br.com.tcc.util.database.dao

import androidx.room.*
import br.com.tcc.model.Roteiro

@Dao
interface RoteiroDAOHelper {

    @Insert
    fun insert(roteiro: Roteiro)

    @Insert
    fun insert(roteiro: ArrayList<Roteiro>)

    @Query("SELECT * FROM ROTEIRO")
    fun selectListaRoteiro(): List<Roteiro>

    @Query("SELECT * FROM ROTEIRO WHERE ID = :id")
    fun selectId(id: Int): Roteiro

    @Query("SELECT * FROM ROTEIRO WHERE CODLOJA = :codLoja")
    fun selectCod(codLoja: Int): Roteiro

    @Query("DELETE FROM ROTEIRO")
    fun deleteAll()

    @Query("UPDATE ROTEIRO SET FLCOLETA = 1, CHECKIN = :dataHora WHERE ID = :id")
    fun realizaCheckin(id: Int, dataHora: String)

    @Query("UPDATE ROTEIRO SET FLCOLETA = 2, CHECKOUT = :dataHora WHERE ID = :id")
    fun realizaCheckout(id: Int, dataHora: String)

    @Query("UPDATE ROTEIRO SET FLCOLETA = 2, CHECKOUT = :dataHora, FLJUSTIFICADA = 1 WHERE ID = :id")
    fun justifica(id: Int, dataHora: String)

    @Query("SELECT * FROM ROTEIRO WHERE ID NOT IN (:idLoja) AND flColeta = 1")
    fun validaCheckin(idLoja: Int) : List<Roteiro>

}