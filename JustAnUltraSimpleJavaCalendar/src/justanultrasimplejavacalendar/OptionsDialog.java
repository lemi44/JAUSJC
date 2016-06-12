/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author Xsior
 */
public class OptionsDialog extends Dialog<Pair<Boolean,String>>{
        
    public OptionsDialog(Boolean b, String s) {                
        super();
        this.setTitle("Opcje");
        ButtonType potwierdzenieButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(potwierdzenieButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        CheckBox saving = new CheckBox();
        saving.setAllowIndeterminate(false);
        if(b!=null)
        {
            saving.setSelected(b);
        }
        TextField savedir = new TextField();
        if(s!=null)
        {
            savedir.setText(s);
        }
        grid.add(new Label("Czy zapisywać zdarzenie przy wychodzeniu oraz wczytywać przy włączaniu programu:"), 0, 0);
        grid.add(saving, 1, 0);
        grid.add(new Label("Podaj scieżkę do folderu w którym mają być zapisywane zdarzenia:"), 0, 1);
        grid.add(savedir, 1, 1);
        
        this.getDialogPane().setContent(grid);
        
        
        this.setResultConverter(new Callback<ButtonType, Pair<Boolean, String>>() {
            @Override
            public Pair<Boolean, String> call(ButtonType dialogButton) {
                if (dialogButton == potwierdzenieButtonType) {
                    return new Pair<>(saving.isSelected(), savedir.getText());
                }
                return null;
            }
        });
    }


    
}
