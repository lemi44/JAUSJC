/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Xsior
 */
public class UsunDialog extends Dialog<Calendar> {
     public UsunDialog() {
        super();
        this.setTitle("Usuwanie zdarzenia");
        this.setHeaderText("Prosze podać datę przed którą zdarzenia mają być usunięte");
        ButtonType potwierdzenieButtonType = new ButtonType("Usuń", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(potwierdzenieButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        DatePicker dtremove = new DatePicker(LocalDate.now());
        grid.add(new Label("Data:"), 0, 2);
        grid.add(dtremove, 1, 2);
        this.getDialogPane().setContent(grid);
        
        this.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == potwierdzenieButtonType) {
                return GregorianCalendar.from(dtremove.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()));
            }
            return null;
        });
        
        
        
     }
}
