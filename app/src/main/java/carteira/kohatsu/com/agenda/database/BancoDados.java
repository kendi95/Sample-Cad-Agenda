package carteira.kohatsu.com.agenda.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import carteira.kohatsu.com.agenda.database.sql.SQL;

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public BancoDados(Context context){
        super(context, "dados", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL.createTableAgenda());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
