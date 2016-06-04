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
import java.util.Optional;
import java.util.ResourceBundle;
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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        
        alert.showAndWait();
    }
    
    @FXML
    private void handlePrevMonthAction() {
        wybranyKalendarz.add(Calendar.MONTH, -1);
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handlePrevYearAction() {
        wybranyKalendarz.add(Calendar.YEAR, -1);
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleNextMonthAction() {
        wybranyKalendarz.add(Calendar.MONTH, 1);
        aktualizujDate();
        aktualizujKomorki();
    }
    
    @FXML
    private void handleNextYearAction() {
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
        for (int i = 0; i < 7; i++) {
            Label l = new Label(dni[i]);
            l.setFont(Font.font("System", FontWeight.BOLD, 18));
            //l.setAlignment(Pos.CENTER);
            tabelaDni.add(l, i, 0);
        }
        aktualizujDate();
        aktualizujKomorki();
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
