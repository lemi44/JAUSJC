/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author Xsior
 */
public class DodajDialog extends Dialog<Zdarzenie>{
    
    public DodajDialog() {
        super();
        this.setTitle("Dodawanie zdarzenia");
        this.setHeaderText("Prosze sprecyzować parametry zdarzenia");
        ButtonType potwierdzenieButtonType = new ButtonType("Dodaj", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(potwierdzenieButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField summary = new TextField();
        summary.setPromptText("Nazwa zdarzenie");
        TextField descript = new TextField();
        descript.setPromptText("Opis zdarzenia");
        DatePicker dtstart = new DatePicker(LocalDate.now());
        DatePicker dtend = new DatePicker(LocalDate.now());


        grid.add(new Label("Nazwa zdarzenia:"), 0, 0);
        grid.add(summary, 1, 0);
        grid.add(new Label("Opis zdarzenia:"), 0, 1);
        grid.add(descript, 1, 1);
        grid.add(new Label("Data startu zdarzenia:"), 0, 2);
        grid.add(dtstart, 1, 2);
        grid.add(new Label("Data końca zdarzenia:"), 0, 3);
        grid.add(dtend, 1, 3);
        Node potwierdzenieButton = this.getDialogPane().lookupButton(potwierdzenieButtonType);
        potwierdzenieButton.setDisable(true);

        summary.textProperty().addListener((observable, oldValue, newValue) -> {
        potwierdzenieButton.setDisable(newValue.trim().isEmpty()); 
        });
        
        this.getDialogPane().setContent(grid);

        Platform.runLater(() -> summary.requestFocus());
        this.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == potwierdzenieButtonType) {
                Calendar data = GregorianCalendar.from(dtstart.getValue().atStartOfDay(ZoneId.systemDefault()));
                Calendar data2 = GregorianCalendar.from(dtend.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()));
                return new Zdarzenie(data,data2,summary.getText(), descript.getText()  );
            }
            return null;
        });


        
    }
    
   
}
