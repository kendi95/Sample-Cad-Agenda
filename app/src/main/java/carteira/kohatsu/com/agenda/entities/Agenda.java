package carteira.kohatsu.com.agenda.entities;

import android.database.Cursor;

import java.io.Serializable;


public class Agenda implements Serializable {

    private Long id;
    private String titulo;
    private String data;
    private String horas;
    private String descricao;

    public Agenda(){}

    public Agenda(Long id, String titulo, String data, String horas, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.horas = horas;
        this.descricao = descricao;
    }

    public Agenda(Cursor cursor){
        this.id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        this.titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
        this.data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
        this.horas = cursor.getString(cursor.getColumnIndexOrThrow("horas"));
        this.descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
