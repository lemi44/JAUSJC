/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Michał
 */
public class UIController implements Initializable {
    
    @FXML private MenuBar menuBar;
    @FXML private MenuItem wyjscieMenuItem;
    @FXML private GridPane tabelaDni;
    @FXML private Label data;
    private GregorianCalendar wybranyKalendarz = new GregorianCalendar();
    private StringProperty wybranaData = new SimpleStringProperty();
    public final String getWybranaData() {return wybranaData.get();}
    public final void setWybranaData(String value){wybranaData.set(value);}
    public StringProperty wybranaDataProperty() {return wybranaData;}
    String dni[] = {"niedziela", "poniedziałek", "wtorek", "środa", "czwartek",
        "piątek", "sobota"};
    String miesiace[] = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj",
        "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad",
        "Grudzień"};

    @FXML
    private void handleDodajAction(ActionEvent event) {
        
    }
    
    @FXML
    private void handleUsunDialogAction() {
        
    }
    
    @FXML
    private void handleFiltrujDialogAction() {
        
    }
    
    @FXML
    private void handleImportICalendarAction() {
        
    }
    
    @FXML
    private void handleImportXMLAction() {
        
    }
    
    @FXML
    private void handleImportDatabaseAction() {
        
    }
    
    @FXML
    private void handleExportICalendarAction() {
        
    }
    
    @FXML
    private void handleExportXMLAction() {
        
    }
    
    @FXML
    private void handleExportDatabaseAction() {
        
    }
    
    @FXML
    private void handleUstawieniaAction() {
        
    }
    
    @FXML
    private void handleWyjscieAction() {
        Platform.exit();
    }
    
    @FXML
    private void handleAboutAction() {
        
    }
    
    @FXML
    private void handlePrevMonthAction() {
        wybranyKalendarz.add(Calendar.MONTH, -1);
        aktualizujDate();
    }
    
    @FXML
    private void handlePrevYearAction() {
        wybranyKalendarz.add(Calendar.YEAR, -1);
        aktualizujDate();
    }
    
    @FXML
    private void handleNextMonthAction() {
        wybranyKalendarz.add(Calendar.MONTH, 1);
        aktualizujDate();
    }
    
    @FXML
    private void handleNextYearAction() {
        wybranyKalendarz.add(Calendar.YEAR, 1);
        aktualizujDate();
    }
    
    private void aktualizujDate() {
        DateFormat fmt = new SimpleDateFormat("MMMM yyyy");
        Date tmpData = wybranyKalendarz.getTime();
        setWybranaData(fmt.format(tmpData));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wyjscieMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Q"));
        for (int i = 0; i < 7; i++) {
            Label l = new Label(dni[i]);
            l.setFont(Font.font("System", FontWeight.BOLD, 18));
            l.setAlignment(Pos.CENTER);
            tabelaDni.add(l, i, 0);
        }
        aktualizujDate();
    }     
    
}
