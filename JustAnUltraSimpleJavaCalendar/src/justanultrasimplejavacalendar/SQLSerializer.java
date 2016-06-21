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
import org.sqlite.SQLiteConfig;
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
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(DB_URL,config.toProperties());
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
            throw e;
        } 
    }
    public boolean createTables()
    {
        String createZdarzenia = "CREATE TABLE IF NOT EXISTS zdarzenia (uid VARCHAR(250) PRIMARY KEY , summary varchar(255), description varchar(255), dtstamp string, dtstart string, dtend string)";
        String createAlarmy = "CREATE TABLE IF NOT EXISTS alarmy (uid VARCHAR(250), description varchar(255) NOT NULL, trigger int NOT NULL, duration int, repeat int, FOREIGN KEY(uid) REFERENCES zdarzenia(uid))";
         try {
            stat.execute(createZdarzenia);
            stat.execute(createAlarmy);
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
                    "insert into zdarzenia values (? , ?, ?, ?, ?, ?);");
            prepStmt.setString(1, z.getUid() );
            prepStmt.setString(2, z.getSummary());
            prepStmt.setString(3, z.getDescription());
            prepStmt.setString(4, strDtStamp );
            prepStmt.setString(5, strDtStart );
            prepStmt.setString(6, strDtEnd ); 
                
            prepStmt.execute();
            
            if(z.isAlarm()==true) {
                prepStmt = conn.prepareStatement(
                    "insert into alarmy values (? , ?, ?, ?, ?);");
                prepStmt.setString(1, z.getUid() );
                prepStmt.setString(2, z.getAlarm().getOpis());
                prepStmt.setInt(3, z.getAlarm().getTrigger());
                if(z.getAlarm().getCzasTrwania()==null)
                    prepStmt.setNull(4, java.sql.Types.INTEGER);
                else
                    prepStmt.setInt(4, z.getAlarm().getCzasTrwania()); 
                if(z.getAlarm().getPowtorzenia()==null)
                    prepStmt.setNull(5, java.sql.Types.INTEGER);
                else
                    prepStmt.setInt(5, z.getAlarm().getPowtorzenia());
                prepStmt.execute();
            }
            
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
            String uid, summary, description, dtstamp, dtstart, dtend;
            Integer trigger, czas, powtorzenia;
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
                set.add(z);
            }
            result = stat.executeQuery("SELECT * FROM alarmy");
            while(result.next()) {
                uid = result.getString("uid");
                description = result.getString("description");
                trigger = result.getInt("trigger");
                czas = result.getInt("duration");
                if(result.wasNull())
                    czas = null;
                powtorzenia = result.getInt("repeat");
                if(result.wasNull())
                    powtorzenia = null;
                Zdarzenie z = null;
                for(Zdarzenie ev : set)
                    if(ev.getUid()==uid) {
                        z = ev;
                        break;
                    }
                if(z!=null) {
                    Przypomnienie a = new Przypomnienie(description, powtorzenia, z, trigger, czas);
                    z.setAlarm(a);
                }
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
        stat.executeUpdate("delete from alarmy");
        stat.executeUpdate("delete from zdarzenia");
    }
    
}
