package carteira.kohatsu.com.agenda;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import carteira.kohatsu.com.agenda.adapters.AgendaAdapter;
import carteira.kohatsu.com.agenda.database.BancoDados;
import carteira.kohatsu.com.agenda.repositories.AgendaRepository;


public class ActMainListAgenda extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private BancoDados db;
    private SQLiteDatabase database;
    private AgendaRepository repo;
    private AgendaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main_list_agenda);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.act_list_agenda);

        createConnection();

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        adapter = new AgendaAdapter(repo.findAll());
        recyclerView.setAdapter(adapter);

        goToCadAgenda();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        adapter = new AgendaAdapter(repo.findAll());
        recyclerView.setAdapter(adapter);
    }


    private void goToCadAgenda(){
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActMainListAgenda.this, ActCadAgenda.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void createConnection(){
        try{
            db = new BancoDados(this);
            database = db.getWritableDatabase();

            repo = new AgendaRepository(database);
        }catch(SQLException e){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Alerta!");
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton("OK", null);
            dialog.show();
        }
    }

}
