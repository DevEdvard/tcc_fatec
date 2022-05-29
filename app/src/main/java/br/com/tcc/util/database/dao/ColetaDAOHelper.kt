package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.tcc.model.Coleta

@Dao
interface ColetaDAOHelper {

    @Query("SELECT * FROM COLETA WHERE desColeta LIKE :desc")
    fun selectColeta(desc: String): Coleta

    @Query("INSERT INTO COLETA VALUES (1, 'COLETA_PRODUTO', 'menu_produto.png', 'menu_produto_ok.png', 1), " +
            " (2, 'COLETA_FOTO_EXECUCAO', 'menu_foto_execucao.png', 'menu_foto_execucao_ok.png', 1)")
    fun insertColetas()

    @Query("DELETE FROM COLETA")
    fun deleteColetas()
}