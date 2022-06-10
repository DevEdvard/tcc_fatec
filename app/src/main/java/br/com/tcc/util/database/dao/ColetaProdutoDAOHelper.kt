package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.tcc.model.ColetaProduto

@Dao
interface ColetaProdutoDAOHelper {

    @Query("SELECT * FROM COLETAPRODUTO WHERE IDPRODUTO in (:id)")
    fun selectColetaProduto(id : Int) : ColetaProduto

//    @Query("INSERT INTO COLETAPRODUTO VALUES (1,1,'teste', 'teste',1,'1.1',2,3)")
//    fun insertTeste()

    @Query("DELETE FROM COLETAPRODUTO")
    fun deleteAll()

    @Insert
    fun insert(mColeta: ColetaProduto)

    @Query("DELETE FROM COLETAPRODUTO WHERE IDPRODUTO = :idProduto")
    fun deleteId(idProduto: Int?)
}