package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.tcc.model.Justificativa

@Dao
interface JustificativaDAOHelper {

    @Query("SELECT * FROM JUSTIFICATIVA")
    fun select(): List<Justificativa>

    @Query("SELECT * FROM JUSTIFICATIVA WHERE ID = :id")
    fun selectId(id: Int): Justificativa

    @Query("INSERT INTO JUSTIFICATIVA VALUES (1, 'ATESTADO MÉDICO'), (2, 'LOJA FECHADA'), (3, 'GERENTE DA LOJA NÃO AUTORIZOU PESQUISA')")
    fun insertTeste()
}