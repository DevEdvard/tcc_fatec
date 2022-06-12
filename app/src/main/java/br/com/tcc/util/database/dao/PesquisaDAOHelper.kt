package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.tcc.model.ColetaProduto
import br.com.tcc.model.Justificativa
import br.com.tcc.model.Pesquisa

@Dao
interface PesquisaDAOHelper {

    @Query("SELECT * FROM JUSTIFICATIVA")
    fun select(): List<Justificativa>

    @Query("SELECT * FROM PESQUISA WHERE ID = :id")
    fun selectId(id: Int): Pesquisa?

    @Query("SELECT * FROM COLETAPRODUTO WHERE CODPESQUISA = :id")
    fun selectP(id: Int?) : List<ColetaProduto?>

    @Query("SELECT * FROM PESQUISA WHERE CODROTEIRO = :codRoteiro")
    fun selectRoteiro(codRoteiro: Int?) : Pesquisa?

    @Insert
    fun insert(pesquisa: Pesquisa)

    @Query("UPDATE PESQUISA SET TRANSMISSAO = :dataHora WHERE ID = :id")
    fun attTransmissao(id: Int, dataHora: String)

    @Query("DELETE FROM PESQUISA WHERE CODROTEIRO = :codRoteiro")
    fun deleteId(codRoteiro: Int)

    @Query("DELETE FROM PESQUISA")
    fun deleteAll()
}