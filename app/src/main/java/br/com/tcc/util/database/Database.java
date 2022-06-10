package br.com.tcc.util.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.tcc.model.Coleta;
import br.com.tcc.model.ColetaProduto;
import br.com.tcc.model.Justificativa;
import br.com.tcc.model.Pesquisa;
import br.com.tcc.model.Roteiro;
import br.com.tcc.model.Sku;
import br.com.tcc.model.Usuario;
import br.com.tcc.util.database.dao.ColetaDAOHelper;
import br.com.tcc.util.database.dao.ColetaProdutoDAOHelper;
import br.com.tcc.util.database.dao.JustificativaDAOHelper;
import br.com.tcc.util.database.dao.PesquisaDAOHelper;
import br.com.tcc.util.database.dao.RoteiroDAOHelper;
import br.com.tcc.util.database.dao.SkuDAOHelper;
import br.com.tcc.util.database.dao.UsuarioDAOHelper;

@androidx.room.Database(entities =
        {
                Usuario.class,
                Roteiro.class,
                Coleta.class,
                Sku.class,
                ColetaProduto.class,
                Justificativa.class,
                Pesquisa.class
        }, version = 39, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static final String NOME_BD = "tcc.db";

    public abstract UsuarioDAOHelper getRoomUsuarioDao();

    public abstract RoteiroDAOHelper getRoomRoteiroDao();

    public abstract ColetaDAOHelper getRoomColetaDao();

    public abstract SkuDAOHelper getRoomSkuDao();

    public abstract ColetaProdutoDAOHelper getRoomColetaProdutoDao();

    public abstract JustificativaDAOHelper getRoomJusitivicativaDao();

    public abstract PesquisaDAOHelper getRoomPesquisaDao();

    public static Database getInstance(Context context) {
        return Room
                .databaseBuilder(context, Database.class, NOME_BD)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
//                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }

}