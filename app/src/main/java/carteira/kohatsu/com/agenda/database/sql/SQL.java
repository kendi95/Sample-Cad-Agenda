package carteira.kohatsu.com.agenda.database.sql;

public class SQL {

    public static String createTableAgenda(){
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS Agenda (");
        sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT(''),");
        sql.append("titulo VARCHAR(20) NOT NULL DEFAULT(''),");
        sql.append("data DATE NOT NULL DEFAULT(''),");
        sql.append("horas DATETIME NOT NULL DEFAULT(''),");
        sql.append("descricao VARCHAR(255) DEFAULT('') )");

        return sql.toString();
    }

    public static String findById(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Agenda WHERE id = ?");

        return sql.toString();
    }

    public static String findAll(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Agenda");

        return sql.toString();
    }

}
