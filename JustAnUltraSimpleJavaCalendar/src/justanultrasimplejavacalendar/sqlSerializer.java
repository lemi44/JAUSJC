/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author Xsior
 */
public class sqlSerializer {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:biblioteka.db";

    private Connection conn;
    private Statement stat;
    
    public sqlSerializer() {
        try {
            Class.forName(sqlSerializer.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
 
        createTables();
    }
    public boolean createTables()
    {
        String createZdarzenia = "CREATE TABLE IF NOT EXISTS zdarzenia (uid VARCHAR(250) PRIMARY KEY , summary varchar(255), description varchar(255), dtstamp string, dtstart string, dtend string)";
         try {
            stat.execute(createZdarzenia);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean insertZdarzenie(Zdarzenie z) {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String strDtStamp = df.format(z.getDtstamp());
            String strDtStart = df.format(z.getDtstart());
            String strDtEnd = df.format(z.getDtend());
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into zdarzenia values (? , ?, ?, ?, ?, ?);");
            prepStmt.setString(1, z.getUid() );
            prepStmt.setString(2, z.getSummary());
            prepStmt.setString(3, z.getDescription());
            prepStmt.setString(4, strDtStamp );
            prepStmt.setString(5, strDtStart );
            prepStmt.setString(6, strDtEnd );
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public HashSet<Zdarzenie> selectZdarzenia() throws ParseException {
        HashSet<Zdarzenie> set = new HashSet<Zdarzenie>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM zdarzenia");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String uid, summary, description, dtstamp, dtstart, dtend ;
            
            while(result.next()) {
                uid = result.getString("uid");
                summary = result.getString("summary");
                description = result.getString("description");
                dtstamp = result.getString("dtstamp");
                dtstart = result.getString("dtstart");
                dtend = result.getString("dtend");
                set.add(new Zdarzenie(df.parse(dtstamp),df.parse(dtstart),df.parse(dtend),uid,summary,description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return set;
    }
     public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}
