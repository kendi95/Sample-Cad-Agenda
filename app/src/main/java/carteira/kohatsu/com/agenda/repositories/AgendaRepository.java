package carteira.kohatsu.com.agenda.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import carteira.kohatsu.com.agenda.database.sql.SQL;
import carteira.kohatsu.com.agenda.entities.Agenda;


public class AgendaRepository {

    private SQLiteDatabase connection;

    public AgendaRepository(SQLiteDatabase connection){
        this.connection = connection;
    }

    public Agenda findById(Long id){
        String[] param = new String[1];
        param[0] = String.valueOf(id);

        Cursor cursor = connection.rawQuery(SQL.findById(), param);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return new Agenda(cursor);
        } else {
            return null;
        }
    }

    public List<Agenda> findAll(){
        List<Agenda> list = new ArrayList<>();

        Cursor cursor = connection.rawQuery(SQL.findAll(), null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                list.add(new Agenda(cursor));
            }while(cursor.moveToNext());
        }

        return list;
    }

    public void insert(Agenda agenda){
        ContentValues values = new ContentValues();

        values.put("titulo", agenda.getTitulo());
        values.put("data", agenda.getData());
        values.put("horas", agenda.getHoras());
        values.put("descricao", agenda.getDescricao());

        connection.insertOrThrow("Agenda", null, values);
    }

    public void update(Agenda agenda){
        String[] param = new String[1];
        param[0] = String.valueOf(agenda.getId());

        ContentValues values = new ContentValues();

        values.put("titulo", agenda.getTitulo());
        values.put("data", agenda.getData());
        values.put("horas", agenda.getHoras());
        values.put("descricao", agenda.getDescricao());

        connection.update("Agenda", values, "id = ?", param);
    }

    public void delete(Long id){
        String[] param = new String[1];
        param[0] = String.valueOf(id);

        connection.delete("Agenda", "id = ?", param);
    }

}
