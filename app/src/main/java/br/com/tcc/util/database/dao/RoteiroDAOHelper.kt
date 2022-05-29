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
    fun select(id: Int): Roteiro

    @Query("DELETE FROM ROTEIRO")
    fun deleteAll()

    @Query(" INSERT INTO ROTEIRO VALUES (1,'COVABRA ITUPEVA', 'COVABRA', '', '', 1, 1,0), " +
            " (2,'AKKI ATACADISTA M. BOI MIRIM','AKKI ATACADISTA', '', '', 1, 1,0), " +
            " (3,'SAM´S CLUB SÃO CAETANO', 'SAMS CLUB', '', '', 1, 1,0) ")
    fun inserirLojasTeste()

    @Query("SELECT * FROM ROTEIRO WHERE ID = :id")
    fun selectLojaRoteiro(id: Int): Roteiro

    @Query("UPDATE ROTEIRO SET FLCOLETA = 1 WHERE ID = :id")
    fun realizaCheckin(id: Int)

    @Query("UPDATE ROTEIRO SET FLCOLETA = 2 WHERE ID = :id")
    fun realizaCheckout(id: Int)

    @Query("SELECT * FROM ROTEIRO WHERE ID NOT IN (:idLoja) AND flColeta = 1")
    fun validaCheckin(idLoja: Int) : List<Roteiro>


}