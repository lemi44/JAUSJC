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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author Xsior
 */
public class SQLSerializer {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:kalendarz.db";

    private Connection conn;
    private Statement stat;
    
    public SQLSerializer() throws SQLException, ClassNotFoundException {
        connect();
 
        createTables();
    }
    public void connect() throws ClassNotFoundException, SQLException
    {
        try {
            Class.forName(SQLSerializer.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
            throw e;
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
            throw e;
        } 
    }
    public boolean createTables()
    {
        String createZdarzenia = "CREATE TABLE IF NOT EXISTS zdarzenia (uid VARCHAR(250) PRIMARY KEY , summary varchar(255), description varchar(255), dtstamp string, dtstart string, dtend string, alarmOpis string, alarmTrigger int, alarmCzas int, alarmPowtorzenia int)";
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
            String strDtStamp = df.format(z.getDtstamp().getTime());
            String strDtStart = df.format(z.getDtstart().getTime());
            String strDtEnd = df.format(z.getDtend().getTime());
            PreparedStatement prepStmt;
            /*if(z.isAlarm()==false){
            prepStmt = conn.prepareStatement(
                    "insert into zdarzenia values (? , ?, ?, ?, ?, ?);");
            prepStmt.setString(1, z.getUid() );
            prepStmt.setString(2, z.getSummary());
            prepStmt.setString(3, z.getDescription());
            prepStmt.setString(4, strDtStamp );
            prepStmt.setString(5, strDtStart );
            prepStmt.setString(6, strDtEnd );
            }
            else{*/
            prepStmt = conn.prepareStatement(
                    "insert into zdarzenia values (? , ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, z.getUid() );
            prepStmt.setString(2, z.getSummary());
            prepStmt.setString(3, z.getDescription());
            prepStmt.setString(4, strDtStamp );
            prepStmt.setString(5, strDtStart );
            prepStmt.setString(6, strDtEnd ); 
            
            if(z.isAlarm()==true){
            
            prepStmt.setInt(8, (int) z.getAlarm().getTrigger());
            prepStmt.setInt(9, (int) z.getAlarm().getCzasTrwania()); 
            prepStmt.setInt(10, (int) z.getAlarm().getPowtorzenia()); 
            }else{
            prepStmt.setString(7, null );
            prepStmt.setString(8, null);
            prepStmt.setString(9, null); 
            prepStmt.setString(10, null); 
            }
                
            prepStmt.execute();
            
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public HashSet<Zdarzenie> selectZdarzenia() throws ParseException, SQLException {
        HashSet<Zdarzenie> set = new HashSet<Zdarzenie>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM zdarzenia");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String uid, summary, description, dtstamp, dtstart, dtend, alarmOpis ;
            int trigger, czas, powtorzenia;
            while(result.next()) {
                uid = result.getString("uid");
                summary = result.getString("summary");
                description = result.getString("description");
                dtstamp = result.getString("dtstamp");
                dtstart = result.getString("dtstart");
                dtend = result.getString("dtend");
                Calendar dtStampCal = new GregorianCalendar();
                dtStampCal.setTime(df.parse(dtstamp));
                Calendar dtStartCal = new GregorianCalendar();
                dtStartCal.setTime(df.parse(dtstart));
                Calendar dtEndCal = new GregorianCalendar();
                dtEndCal.setTime(df.parse(dtend));
                Zdarzenie z = new Zdarzenie(dtStampCal,dtStartCal,dtEndCal,uid,summary,description);
                if(result.getString("alarmOpis")!=null)
                {
                    alarmOpis = result.getString("alarmOpis");
                    trigger = result.getInt("alarmTrigger");
                    czas = result.getInt("alarmCzas");
                    powtorzenia = result.getInt("alarmPowtorzenia");
                    
                    Przypomnienie a = new Przypomnienie(alarmOpis, powtorzenia, z, trigger, czas);
                }
                set.add(z);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return set;
    }
     public void closeConnection() throws SQLException {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
            throw e;
        }
    }
    public void delZdarzenia() throws SQLException {
        stat.executeUpdate("delete from zdarzenia");
    }
    
}
