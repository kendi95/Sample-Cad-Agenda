package carteira.kohatsu.com.agenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import carteira.kohatsu.com.agenda.R;
import carteira.kohatsu.com.agenda.database.BancoDados;
import carteira.kohatsu.com.agenda.entities.Agenda;
import carteira.kohatsu.com.agenda.pickers.DatePickerFragment;
import carteira.kohatsu.com.agenda.pickers.TimePickerFragment;
import carteira.kohatsu.com.agenda.repositories.AgendaRepository;

public class ActCadAgenda extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextInputLayout textLayoutTitulo, textLayoutData, textLayoutHoras;
    private TextInputEditText txtInputData, txtInputHoras, txtInputTitulo, txtInputDescricao, txtInputId;
    private BancoDados db;
    private SQLiteDatabase database;
    private AgendaRepository repo;
    private Agenda agenda;
    private LinearLayout linerLayoutCadAgenda;
    private String titulo, data, horas, descricao;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cad_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        linerLayoutCadAgenda = (LinearLayout) findViewById(R.id.linerLayoutCadAgenda);
        setSupportActionBar(toolbar);

        txtInputId = (TextInputEditText) findViewById(R.id.txtInputId);
        txtInputData = (TextInputEditText) findViewById(R.id.txtInputData);
        txtInputHoras = (TextInputEditText) findViewById(R.id.txtInputHoras);
        txtInputTitulo = (TextInputEditText) findViewById(R.id.txtInputTitulo);
        txtInputDescricao = (TextInputEditText) findViewById(R.id.txtInputDescricao);

        textLayoutTitulo = (TextInputLayout) findViewById(R.id.textLayoutTitulo);
        textLayoutData = (TextInputLayout) findViewById(R.id.textLayoutData);
        textLayoutHoras = (TextInputLayout) findViewById(R.id.textLayoutHoras);

        openCalendar();
        openClock();

        createConnection();

        verifyParam();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_agenda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_delete:
                removeItem();
                break;

            case R.id.menu_ok:
                confirm();
                break;

            case R.id.menu_cancel:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");

//        String dataAtual = DateFormat.getDateInstance().format(calendar.getTime());

        String dataAtual = format.format(calendar.getTime());

        txtInputData.setText(dataAtual);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        String horasAtual = format.format(calendar.getTime());

        txtInputHoras.setText(horasAtual);
    }

    private void openCalendar(){
        txtInputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void openClock(){
        txtInputHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    private void confirm() {
        if(validateFields() == false){
            try{
                if(txtInputId.getText().toString().isEmpty()){
                    insert();
                } else {
                    update();
                }

            }catch(SQLException e){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Alerta!");
                dialog.setMessage(e.getMessage());
                dialog.setNeutralButton("OK", null);
                dialog.show();
            }

        }
    }

    private void insert(){
        agenda = new Agenda(null, titulo, data, horas, descricao);
        repo.insert(agenda);
        Toast.makeText(this, "Compromisso salvo com sucesso.", Toast.LENGTH_LONG).show();
        finish();
    }

    private void update(){
        agenda = new Agenda(id, titulo, data, horas, descricao);
        repo.update(agenda);
        Toast.makeText(this, "Compromisso atualizado com sucesso.", Toast.LENGTH_LONG).show();
        finish();
    }

    private void removeItem(){
        if(!txtInputId.getText().toString().isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Aviso!");
            dialog.setMessage("Deseja realmente excluir?");

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    repo.delete(agenda.getId());
                    Toast.makeText(ActCadAgenda.this, "Compromisso deletado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialog.show();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Aviso!");
            dialog.setMessage("Não foi possível excluir pois não há existencia desse compromisso.");
            dialog.setNeutralButton("OK", null);
            dialog.show();
        }
    }

    private boolean validateFields() {
        boolean result = false;

        if(txtInputId.getText().toString().isEmpty()){
            titulo = txtInputTitulo.getText().toString();
            data = txtInputData.getText().toString();
            horas = txtInputHoras.getText().toString();
            descricao = txtInputDescricao.getText().toString();
        }else{
            id = Long.parseLong(txtInputId.getText().toString());
            titulo = txtInputTitulo.getText().toString();
            data = txtInputData.getText().toString();
            horas = txtInputHoras.getText().toString();
            descricao = txtInputDescricao.getText().toString();
        }

        if(result = isCamposVazio(titulo)){
            textLayoutTitulo.setErrorEnabled(true);
            textLayoutTitulo.setError("Campo vazio.");
        } else if(result = isCamposVazio(data)){
            textLayoutTitulo.setErrorEnabled(false);
            textLayoutTitulo.setError(null);
            textLayoutData.setErrorEnabled(true);
            textLayoutData.setError("Campo vazio.");
        } else if(result = isCamposVazio(horas)){
            textLayoutData.setErrorEnabled(false);
            textLayoutData.setError(null);
            textLayoutHoras.setErrorEnabled(true);
            textLayoutHoras.setError("Campo vazio.");
        } else {
            textLayoutHoras.setErrorEnabled(false);
            textLayoutHoras.setError(null);
        }
        return result;
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

    private boolean isCamposVazio(String valor){
        boolean res = (TextUtils.isEmpty(valor) || valor.trim().isEmpty() || valor == null);
        return res;
    }

    private void verifyParam(){
        Bundle bundle = getIntent().getExtras();

        if((bundle != null) && (bundle.containsKey("agenda"))){
            agenda = (Agenda) bundle.getSerializable("agenda");

            txtInputId.setText(agenda.getId().toString());
            txtInputTitulo.setText(agenda.getTitulo());
            txtInputData.setText(agenda.getData());
            txtInputHoras.setText(agenda.getHoras());
            txtInputDescricao.setText(agenda.getDescricao());
        }
    }

}
