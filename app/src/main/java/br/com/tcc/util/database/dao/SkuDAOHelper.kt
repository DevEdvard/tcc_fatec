package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.tcc.model.Sku

@Dao
interface SkuDAOHelper {

    @Query("INSERT INTO SKU VALUES " +
            " (1,'Produto tipo 1', 'CAMIL', '10.90', '16,80', 1, 1, 0)," +
            " (2,'Produto tipo 2', 'CAMIL', '10.90', '16,80', 1, 1, 0)," +
            " (3,'Produto tipo 3', 'CAMIL', '10.90', '16,80', 1, 1, 0)," +
            " (4,'Produto tipo 1', 'CAMIL', '10.90', '16,80', 2, 0, 0)," +
            " (5,'Produto tipo 2', 'CAMIL', '10.90', '16,80', 2, 0, 0)," +
            " (6,'Produto tipo 3', 'CAMIL', '10.90', '16,80', 2, 0, 0)," +
            " (8,'Produto tipo 1', 'CAMIL', '10.90', '16,80', 3, 0, 0)," +
            " (9,'Produto tipo 2', 'CAMIL', '10.90', '16,80', 3, 0, 0)," +
            " (10,'Produto tipo 3', 'CAMIL', '10.90', '16,80', 3, 0, 0)")
    fun insertSkuTeste()

    @Query("SELECT * FROM SKU WHERE VINCULOLOJA = :id AND FLCOLETADO NOT IN (1)")
    fun selectListaSku(id: Int): List<Sku>

    @Query("SELECT * FROM SKU WHERE VINCULOLOJA = :id")
    fun selectListaSkuColeta(id: Int): List<Sku>

    @Query("SELECT * FROM SKU WHERE ID = :id")
    fun select(id: Int): Sku

    @Query("DELETE FROM SKU")
    fun deleteAll()

    @Query("UPDATE SKU SET FLCOLETADO = 1 WHERE ID = :id")
    fun updateColetado(id: Int)

    @Query("SELECT * FROM SKU WHERE VINCULOLOJA = :id AND ID NOT IN (SELECT IDPRODUTO FROM COLETAPRODUTO)")
    fun selectModuloColetado(id: Int) : List<Sku>

    @Query("SELECT * FROM SKU")
    fun selectAll() : List<Sku>
}