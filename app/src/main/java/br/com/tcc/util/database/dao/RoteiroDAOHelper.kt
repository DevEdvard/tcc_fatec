package br.com.tcc.util.database.dao

import androidx.room.*
import br.com.tcc.model.Roteiro

@Dao
interface RoteiroDAOHelper {

    @Insert
    fun insert(roteiro: Roteiro)

    @Query("SELECT * FROM ROTEIRO")
    fun selectListaRoteiro(): List<Roteiro>

    @Query("SELECT * FROM ROTEIRO WHERE ID = :id")
    fun selectId(id: Int): Roteiro

    @Query("DELETE FROM ROTEIRO")
    fun deleteAll()

    @Query(" INSERT INTO ROTEIRO VALUES (1,1,'COVABRA ITUPEVA', 'COVABRA', '', '', 1, 1,0), " +
            " (2,1,'AKKI ATACADISTA M. BOI MIRIM','AKKI ATACADISTA', '', '', 1, 1,0), " +
            " (3,1,'SAM´S CLUB SÃO CAETANO', 'SAMS CLUB', '', '', 1, 1,0) ")
    fun inserirLojasTeste()

    @Query("SELECT * FROM ROTEIRO WHERE ID = :id")
    fun selectLojaRoteiro(id: Int): Roteiro

    @Query("UPDATE ROTEIRO SET FLCOLETA = 1, CHECKIN = :dataHora WHERE ID = :id")
    fun realizaCheckin(id: Int, dataHora: String)

    @Query("UPDATE ROTEIRO SET FLCOLETA = 2, CHECKOUT = :dataHora WHERE ID = :id")
    fun realizaCheckout(id: Int, dataHora: String)

    @Query("SELECT * FROM ROTEIRO WHERE ID NOT IN (:idLoja) AND flColeta = 1")
    fun validaCheckin(idLoja: Int) : List<Roteiro>

}