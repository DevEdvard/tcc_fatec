package br.com.tcc.util.database;

import androidx.room.migration.Migration;

public class DbMigrations {

//    private static final Migration MIGRATION_1_2 = new Migration(7, 8) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE ROTEIRO (id, nomFantasia, bandeira, checkin, checkout)");
//        }
//    };
//    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
//                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                    "`nome` TEXT, " +
//                    "`telefone` TEXT, " +
//                    "`email` TEXT)");
//            database.execSQL("INSERT INTO ALUNO_NOVO (id,nome,telefone,email) " +
//                    "SELECT id, nome, telefone, email FROM ALUNO");
//            database.execSQL("DROP TABLE ALUNO");
//            database.execSQL("ALTER TABLE ALUNO_NOVO RENAME TO ALUNO");
//        }
//    };
//    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE ALUNO ADD COLUMN logCadastro INTEGER");
//        }
//    };

    static final Migration[] TODAS_MIGRATIONS = {};
}
