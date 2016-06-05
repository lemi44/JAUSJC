/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.util.HashSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Xsior
 */
public class WyszukaneAlert extends Alert{
    
        
        

    public WyszukaneAlert(HashSet<Zdarzenie> z,AlertType alertType) {
        super(alertType);
        this.setTitle("Przefiltrowane zdarzenia:");
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(loginButtonType);
        TableView wyniki = new TableView(); 
        wyniki.setEditable(false);
        TableColumn dtstart = new TableColumn("Data początkowa");
        dtstart.setCellValueFactory(
        new PropertyValueFactory<Zdarzenie,String>("dtstart")
        );

        TableColumn dtend = new TableColumn("Data końcowa");
        dtend.setCellValueFactory(
        new PropertyValueFactory<Zdarzenie,String>("dtend")
        );
        TableColumn summary = new TableColumn("Nazwa zdarzenia");
        summary.setCellValueFactory(
        new PropertyValueFactory<Zdarzenie,String>("Summary")
        );
        TableColumn desc = new TableColumn("Opis zdarzenia");
        desc.setCellValueFactory(
        new PropertyValueFactory<Zdarzenie,String>("Description")
        );
        wyniki.getColumns().addAll(dtstart,dtend,summary,desc);
        final ObservableList<Zdarzenie> data = FXCollections.observableArrayList(z);
        wyniki.setItems(data);

        this.getDialogPane().setContent(wyniki);
        this.setResizable(true);
        this.getDialogPane().setPrefSize(600, 500);
        this.showAndWait();

    }

}
