package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.tcc.model.Justificativa
import br.com.tcc.model.Pesquisa

@Dao
interface PesquisaDAOHelper {

    @Query("SELECT * FROM JUSTIFICATIVA")
    fun select(): List<Justificativa>

    @Query("INSERT INTO JUSTIFICATIVA VALUES (1, 'ATESTADO MÉDICO'), (2, 'LOJA FECHADA'), (3, 'GERENTE DA LOJA NÃO AUTORIZOU PESQUISA')")
    fun insertTeste()

    @Query("DELETE FROM PESQUISA WHERE ID = :id")
    fun deleteId(id: Int)

    @Query("SELECT * FROM PESQUISA WHERE ID = :id")
    fun selectId(id: Int?) : Pesquisa

    @Insert
    fun insert(pesquisa: Pesquisa)
}