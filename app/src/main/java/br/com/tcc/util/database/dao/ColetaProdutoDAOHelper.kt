package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.tcc.model.ColetaProduto

@Dao
interface ColetaProdutoDAOHelper {

    @Query("SELECT * FROM COLETAPRODUTO WHERE IDPRODUTO in (:id)")
    fun selectColetaProduto(id : Int) : ColetaProduto

    @Query("DELETE FROM COLETAPRODUTO")
    fun deleteAll()

    @Insert
    fun insert(mColeta: ColetaProduto)

    @Query("DELETE FROM COLETAPRODUTO WHERE IDPRODUTO = :idProduto")
    fun deleteId(idProduto: Int?)

    @Query("UPDATE COLETAPRODUTO SET CODPESQUISA = :codNovo WHERE CODPESQUISA = :codVelho")
    fun updateCodPesquisa(codVelho: Int, codNovo: Int)
}