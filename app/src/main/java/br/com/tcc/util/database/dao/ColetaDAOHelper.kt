package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.tcc.model.Coleta

@Dao
interface ColetaDAOHelper {

    @Query("SELECT * FROM COLETA WHERE desColeta LIKE :desc")
    fun selectColeta(desc: String): Coleta

    @Query("INSERT INTO COLETA VALUES (1, 'COLETA_PRODUTO'), " +
            " (2, 'COLETA_FOTO_EXECUCAO')")
    fun insertColetas()

    @Query("DELETE FROM COLETA")
    fun deleteAll()
}