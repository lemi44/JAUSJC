/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import javafx.scene.control.Alert;

/**
 *
 * @author lemi44
 */
public class PrzypomnienieAlert extends Alert {
    public final static String ZDARZENIE_STRING_FORMAT = "Przypominam!\n"+
            "Nazwa:%s\n" + "Opis:%s\n" + "Data początkowa:%s\n"+
            "Data końcowa:%s";
    public PrzypomnienieAlert(Zdarzenie zdarzenie) {
        super(AlertType.INFORMATION);
        this.setTitle("Przypomnienie");
        this.setContentText(String.format(ZDARZENIE_STRING_FORMAT,
                zdarzenie.getSummary(), zdarzenie.getDescription(),
                zdarzenie.getDateStart(), zdarzenie.getDateEnd()));
        this.showAndWait();
    }
    
}
