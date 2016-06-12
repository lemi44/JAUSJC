/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

/**
 *
 * @author Michał
 */
public class UIController implements Initializable {
    
    @FXML private MenuBar menuBar;
    @FXML private MenuItem wyjscieMenuItem;
    @FXML private GridPane tabelaDni;
    @FXML private Label data;
    @FXML private GridPane komorkiKalendarza;
    private GregorianCalendar wybranyKalendarz = new GregorianCalendar();
    private StringProperty wybranaData = new SimpleStringProperty();
    private KalendarzModel model = new KalendarzModel();
    private SQLSerializer sql;
    private boolean saveOnExit=false;
    private String saveDir="";
    
    public final String getWybranaData() {return wybranaData.get();}
    public final void setWybranaData(String value){wybranaData.set(value);}
    public StringProperty wybranaDataProperty() {return wybranaData;}
    String dni[] = {"pn", "wt", "śr", "cz",
        "pt", "sb", "nd"};

    @FXML
    private void handleDodajAction(ActionEvent event) {
        Dialog<Zdarzenie> dialog = new DodajDialog();
        Optional<Zdarzenie> result = dialog.showAndWait();
        result.ifPresent(ev -> {
            model.add(ev);
            aktualizujDate();
            aktualizujKomorki();
        });
    }
    
    @FXML
    private void handleUsunDialogAction(ActionEvent event) {
        Dialog<Calendar> dialog = new UsunDialog();
        Optional<Calendar> result = dialog.showAndWait();
        result.ifPresent(ev -> {
            model.delByDate(ev);
            aktualizujDate();
            aktualizujKomorki();
        });
    }
    
    @FXML
    private void handleFiltrujDialogAction(ActionEvent event) {
        Dialog<HashSet<Zdarzenie>> dialog;
        dialog = new FiltrujDialog((HashSet<Zdarzenie>) model.getKolekcja());
        Optional<HashSet<Zdarzenie>> result = dialog.showAndWait();
        result.ifPresent(ev -> {
            Alert x = new WyszukaneAlert(ev,AlertType.NONE);
        });
    }
    
    @FXML
    private void handleImportICalendarAction(ActionEvent event) {
        ICalendarSerializer cal = new ICalendarSerializer();
        if(saveDir.length()>1&&saveDir!=null)
            cal.setDir(saveDir);
        
        model = cal.loadKalendarz();
        aktualizujDate();
        aktualizujKomorki();
        cal = null;
    }
    
    @FXML
    private void handleImportXMLAction(ActionEvent event) {
        /*XMLSerializer xml;
        if(saveDir.length()>1)
            xml = new XMLSerializer(saveDir);
        else
            xml = new XMLSerializer();
        model.fromXml(xml);
        aktualizujDate();
        aktualizujKomorki();
        xml = null;*/
        XMLSerialization2 xml = new XMLSerialization2();
        model = xml.loadKalendarz();
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleImportDatabaseAction(ActionEvent event) {
        sql.connect();
        
        try {
            model.fromSql(sql);
        } catch (ParseException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleExportICalendarAction(ActionEvent event) {
        
        ICalendarSerializer cal = new ICalendarSerializer();
        if(saveDir.length()>1)
            cal.setDir(saveDir);
        cal.saveKalendarz(model);

    }
    
    @FXML
    private void handleExportXMLAction(ActionEvent event) {
        XMLSerializer xml;
        if(saveDir.length()>1)
            xml = new XMLSerializer(saveDir);
        else
            xml = new XMLSerializer();
        model.toXml(xml);
        xml.close();
        XMLSerialization2 x = new XMLSerialization2();
        x.saveKalendarz(model);
    }
    
    @FXML
    private void handleExportDatabaseAction(ActionEvent event) {
        try {
            sql.delZdarzenia();
        } catch (SQLException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            model.toSql(sql);
        } catch (ParseException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql.closeConnection();
    }
    
    @FXML
    private void handleUstawieniaAction(ActionEvent event) {
        Dialog<Pair<Boolean,String>> dialog = new OptionsDialog(saveOnExit, saveDir);
        Optional<Pair<Boolean,String>> result = dialog.showAndWait();
        result.ifPresent(ev -> {
            Pair<Boolean,String> p = ev;
            saveOnExit = p.getKey();
            saveDir = p.getValue();
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                OptionsSerializer o = new OptionsSerializer();
                o.save(saveOnExit, saveDir);
                if(saveOnExit == true)
                {
                    ICalendarSerializer cal = new ICalendarSerializer();
                    cal.setName("autosave.ics");
                    cal.saveKalendarz(model);
                }
            }
        });
        
    }
    
    @FXML
    private void handleWyjscieAction(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void handleAboutAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        
        alert.showAndWait();
    }
    
    @FXML
    private void handlePrevMonthAction(ActionEvent event) {
        wybranyKalendarz.add(Calendar.MONTH, -1);
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handlePrevYearAction(ActionEvent event) {
        wybranyKalendarz.add(Calendar.YEAR, -1);
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleNextMonthAction(ActionEvent event) {
        wybranyKalendarz.add(Calendar.MONTH, 1);
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleNextYearAction(ActionEvent event) {
        wybranyKalendarz.add(Calendar.YEAR, 1);
        aktualizujDate();
        aktualizujKomorki();
    }
    
    private void aktualizujDate() {
        DateFormat fmt = new SimpleDateFormat("MMMM yyyy");
        Date tmpData = wybranyKalendarz.getTime();
        setWybranaData(fmt.format(tmpData));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wyjscieMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Q"));
        OptionsSerializer o = new OptionsSerializer();
        Pair<Boolean,String> p = o.load();
            saveOnExit = p.getKey();
            saveDir = p.getValue();
        for (int i = 0; i < 7; i++) {
            Label l = new Label(dni[i]);
            l.setFont(Font.font("System", FontWeight.BOLD, 18));
            //l.setAlignment(Pos.CENTER);
            tabelaDni.add(l, i, 0);
        }
        File f = new File("autosave.ics");
        if(saveOnExit==true&&f.exists() && !f.isDirectory()){
            ICalendarSerializer cal = new ICalendarSerializer();
            cal.setName("autosave.ics");
            model = cal.loadKalendarz();
            cal = null;
        }
        aktualizujDate();
        aktualizujKomorki();
        sql = new SQLSerializer();
    }     

    private void aktualizujKomorki() {
        komorkiKalendarza.getChildren().clear();
        for(int j=0; j<6;j++) {
            for(int i=1;i<8;i++) {
                GregorianCalendar tmpCal = new GregorianCalendar();
                tmpCal.set(Calendar.YEAR, wybranyKalendarz.get(Calendar.YEAR));
                tmpCal.set(Calendar.MONTH, wybranyKalendarz.get(Calendar.MONTH));
                tmpCal.set(Calendar.DAY_OF_MONTH, 1);
                int firstDayOffset = tmpCal.get(Calendar.DAY_OF_WEEK);
                int przesuniecie = 0;
                if(-firstDayOffset + 2 > 0)
                    przesuniecie = -1;
                else if(-firstDayOffset + 2 < -6)
                    przesuniecie = 1;
                tmpCal.add(Calendar.DAY_OF_MONTH, (firstDayOffset - 1) * -1 + (i + 7*j) + przesuniecie*7);
                komorkiKalendarza.add(new KomorkaController(tmpCal, model), i - 1, j);
            }
            
        }
    }
    
}
