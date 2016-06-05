/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Michał
 */
public class KomorkaController extends VBox {
    @FXML private Label dzien;
    @FXML private ListView listaZdarzen;
    private ObservableList<Pair<String,Zdarzenie>> prawdziwaListaZdarzen;
    //private ObservableList<Zdarzenie> prawdziwaListaZdarzen;
    private GregorianCalendar calendar = new GregorianCalendar();
    
    public KomorkaController(KalendarzModel model) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Komorka.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        dzien.backgroundProperty().set(new Background(new BackgroundFill(
                Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        prawdziwaListaZdarzen = FXCollections.observableArrayList();
        updateCell(model);
    }
    
    public KomorkaController(GregorianCalendar cal, KalendarzModel model) {
        this(model);
        setCalendar(cal,model);
    }
    
    public void setCalendar(GregorianCalendar calendar, KalendarzModel model) {
        this.calendar = calendar;
        updateCell(model);
    }
    
    public GregorianCalendar getCalendar() {
        return calendar;
    }
    
    private void updateCell(KalendarzModel model) {
        dzien.setText(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        prawdziwaListaZdarzen.clear();
        ObservableList<Zdarzenie> lista = model.eventCheck(calendar.getTime());
        ObservableList<String> listaStringow = FXCollections.observableArrayList();
        lista.forEach(item -> {
            String temp = item.getSummary();
            String temp2;
            do {
                temp2 = temp;
            prawdziwaListaZdarzen.forEach(pair -> {
                if(temp == pair.getKey())
                    temp.concat("_");
            });
            } while (temp2 != temp);
            prawdziwaListaZdarzen.add(new Pair(temp,item));
            listaStringow.add(temp);
        });
        // TODO: Zastąpić stringi klasą pochodną po ListCell, aby zastąpić ten
        //  workaround z parami String,Zdarzenie
        listaZdarzen.setItems(listaStringow);
    }
    
       
    
}
