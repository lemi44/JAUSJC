/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javax.xml.bind.JAXBException;

/**
 *
 * @author Michał
 */
public class UIController implements Initializable {
    
    @FXML private MenuBar menuBar;
    @FXML private MenuItem wyjscieMenuItem;
    @FXML private MenuItem dodajMenuItem;
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
        
        try {
            model = cal.loadKalendarz();
        } catch (IOException | ParseException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd deserializacji danych: "+ex.getLocalizedMessage());
            exceptionAlert.showAndWait();
        }
        if(model==null)
            model = new KalendarzModel();
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
        if(saveDir.length()>1&&saveDir!=null)
            xml.setDir(saveDir);
        try{
        model = xml.loadKalendarz();
        } catch (JAXBException | FileNotFoundException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd deserializacji danych: "+ex.getLocalizedMessage());
            exceptionAlert.showAndWait();
        }
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleImportDatabaseAction(ActionEvent event) {
        try {
            sql.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd otwierania połączenia z bazą danych: "+ex.getLocalizedMessage());
            exceptionAlert.showAndWait();
        }
        
        try {
            model.fromSql(sql);
        } catch (ParseException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd deserializacji danych: "+ex.getLocalizedMessage());
            exceptionAlert.showAndWait();
        }
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleExportICalendarAction(ActionEvent event) {
        
        ICalendarSerializer cal = new ICalendarSerializer();
        if(saveDir.length()>1)
            cal.setDir(saveDir);
        try {
            cal.saveKalendarz(model);
        } catch (IOException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd serializacji danych: "+ex.getLocalizedMessage());
            exceptionAlert.showAndWait();
        }

    }
    
    @FXML
    private void handleExportXMLAction(ActionEvent event) {
        /*XMLSerializer xml;
        if(saveDir.length()>1)
            xml = new XMLSerializer(saveDir);
        else
            xml = new XMLSerializer();
        model.toXml(xml);
        xml.close();*/
        XMLSerialization2 x = new XMLSerialization2();
        if(saveDir.length()>1&&saveDir!=null)
            x.setDir(saveDir);
        try {
            x.saveKalendarz(model);
        } catch (JAXBException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd serializacji danych: "+ex.getLinkedException().getLocalizedMessage());
            exceptionAlert.showAndWait();
        }
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
        try {
            sql.closeConnection();
        } catch (SQLException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd zamykania bazych danych: "+ex.getLocalizedMessage());
            exceptionAlert.showAndWait();
        }
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

        
    }
    
    @FXML
    private void handleWyjscieAction(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void handleAboutAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("O programie");
        alert.setHeaderText("JAUS Java Calendar v1.0");
        alert.setContentText("JustAnUltraSimple Java Calendar jest to program typu kalendarz/terminarz. "
                + "\nTwórcy: Michał Leśniak oraz Jakub Florczyk \nStrona: http://lemi44.tk ");
        
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
        if(saveOnExit == true)
                {
                    ICalendarSerializer cal = new ICalendarSerializer();
                    cal.setName("autosave.ics");
                    try {
                        cal.saveKalendarz(model);
                    } catch (IOException ex) {
                        saveOnExit=false;
                        Alert exceptionAlert = new Alert(AlertType.ERROR);
                        exceptionAlert.setContentText("Błąd serializacji danych: "+ex.getLocalizedMessage());
                        exceptionAlert.showAndWait();
                    }
                }
        Alarm.getInstance().sprawdzIUstawModel(model);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wyjscieMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Q"));
        dodajMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));
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
            try {
                model = cal.loadKalendarz();
            } catch (IOException | ParseException ex) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setContentText("Błąd deserializacji danych: "+ex.getLocalizedMessage());
                exceptionAlert.showAndWait();
            }
            cal = null;
        }
        Zdarzenie z = new Zdarzenie();

        aktualizujDate();
        aktualizujKomorki();
        try {
            sql = new SQLSerializer();
        } catch (SQLException | ClassNotFoundException ex) {
            Alert exceptionAlert = new Alert(AlertType.ERROR);
            exceptionAlert.setContentText("Błąd tworzenia połączenia z bazą danych: "+ex.getLocalizedMessage());
            exceptionAlert.showAndWait();
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                aktualizujDate();
            }
           });
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
