/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Xsior
 */
public class FiltrujDialog extends Dialog<HashSet<Zdarzenie>>{
    
    public FiltrujDialog(HashSet<Zdarzenie> kolekcja) {
        super();
        this.setTitle("Filtrowanie zdarzeń");
        this.setHeaderText("Prosze podać fragment opisu lub nazwy zdarzeń które chcesz wyszukać");
        ButtonType potwierdzenieButtonType = new ButtonType("Filtruj", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(potwierdzenieButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField summary = new TextField();
        summary.setPromptText("Szukaj");
        
        grid.add(new Label("Nazwa zdarzenia:"), 0, 0);
        grid.add(summary, 1, 0);
        Node potwierdzenieButton = this.getDialogPane().lookupButton(potwierdzenieButtonType);
        potwierdzenieButton.setDisable(true);

        summary.textProperty().addListener((observable, oldValue, newValue) -> {
        potwierdzenieButton.setDisable(newValue.trim().isEmpty()); 
        });
        
        this.getDialogPane().setContent(grid);

        Platform.runLater(() -> summary.requestFocus());
        this.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == potwierdzenieButtonType) {
                HashSet<Zdarzenie> f = new HashSet<Zdarzenie>();
                for(Zdarzenie i: kolekcja)
                {
                    if(i.getSummary().contains(summary.getText())|| i.getDescription().contains(summary.getText()))
                    {
                        f.add(i);
                    }
                }
                return f;
            }
            return null;
        });
    
    }
}
