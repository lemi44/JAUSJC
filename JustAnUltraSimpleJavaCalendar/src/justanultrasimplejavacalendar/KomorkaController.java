/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Michał
 */
public class KomorkaController extends VBox {
    @FXML private Label dzien;
    @FXML private ListView listaZdarzen;
    private GregorianCalendar calendar = new GregorianCalendar();
    
    public KomorkaController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
"Komorka.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        dzien.backgroundProperty().set(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        updateCell();
    }
    
    public KomorkaController(GregorianCalendar cal) {
        this();
        setCalendar(cal);
    }
    
    public void setCalendar(GregorianCalendar calendar) {
        this.calendar = calendar;
        updateCell();
    }
    
    public GregorianCalendar getCalendar() {
        return calendar;
    }
    
    private void updateCell() {
        dzien.textProperty().set(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
    }

       
    
}
