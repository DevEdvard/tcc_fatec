package br.com.tcc.util.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.tcc.model.Usuario

@Dao
interface UsuarioDAOHelper {

    @Insert
    fun insert(usuario: Usuario)

    @Query("SELECT * FROM USUARIO WHERE LOGIN = :login")
    fun selectUsuarioNome(login: String): Usuario

    @Query("SELECT * FROM USUARIO")
    fun select(): List<Usuario>

    @Delete
    fun deletarUsuario(usuario: Usuario)

    @Query("DELETE FROM USUARIO")
    fun deleteAll()

}