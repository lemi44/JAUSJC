/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;

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
        Slider starth = new Slider();
        starth.setMin(0);
        starth.setMax(23);
        starth.setValue(10);
        starth.setShowTickLabels(true);
        starth.setShowTickMarks(true);
        starth.setMajorTickUnit(1);
        starth.setMinorTickCount(1);
        starth.setBlockIncrement(1);
        Slider startm = new Slider();
        startm.setMin(0);
        startm.setMax(60);
        startm.setValue(15);
        startm.setShowTickLabels(true);
        startm.setShowTickMarks(true);
        startm.setMajorTickUnit(15);
        startm.setMinorTickCount(5);
        startm.setBlockIncrement(1);

        Label TimeStartH = new Label();
        Label TimeStartM = new Label();
        TimeStartH.setDisable(true);
        TimeStartM.setDisable(true);
        TimeStartH.setText(Math.round(starth.getValue()) + "");
        TimeStartM.setText(Math.round(startm.getValue()) + "");
        starth.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            if (newValue == null) {
                TimeStartH.setText("");
            return;
            }
            TimeStartH.setText(Math.round(newValue.intValue()) + "");
        }
        });
        startm.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            if (newValue == null) {
                TimeStartM.setText("");
            return;
            }
            TimeStartM.setText(Math.round(newValue.intValue()) + "");
        }
        });
        DatePicker dtend = new DatePicker(LocalDate.now());
        Slider endh = new Slider();
        endh.setMin(0);
        endh.setMax(23);
        endh.setValue(10);
        endh.setShowTickLabels(true);
        endh.setShowTickMarks(true);
        endh.setMajorTickUnit(1);
        endh.setMinorTickCount(1);
        endh.setBlockIncrement(1);
        Slider endm = new Slider();
        endm.setMin(0);
        endm.setMax(60);
        endm.setValue(15);
        endm.setShowTickLabels(true);
        endm.setShowTickMarks(true);
        endm.setMajorTickUnit(15);
        endm.setMinorTickCount(5);
        endm.setBlockIncrement(1);
        Label TimeEndH = new Label();
        Label TimeEndM = new Label();
        TimeEndH.setDisable(true);
        TimeEndM.setDisable(true);
        TimeEndH.setText(Math.round(endh.getValue()) + "");
        TimeEndM.setText(Math.round(endm.getValue()) + "");
        endh.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            if (newValue == null) {
                TimeEndH.setText("");
            return;
            }
            TimeEndH.setText(Math.round(newValue.intValue()) + "");
        }
        });
        endm.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            if (newValue == null) {
                TimeEndM.setText("");
            return;
            }
            TimeEndM.setText(Math.round(newValue.intValue()) + "");
        }
        });
        
        CheckBox alarm = new CheckBox();
        alarm.setAllowIndeterminate(false);
        TextField czas =  new TextField() {
            @Override public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
        TextField trwanie =  new TextField() {
            @Override public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
        TextField powt =  new TextField() {
            @Override public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
        grid.add(new Label("Nazwa zdarzenia:"), 0, 0);
        grid.add(summary, 1, 0);
        grid.add(new Label("Opis zdarzenia:"), 0, 1);
        grid.add(descript, 1, 1);
        grid.add(new Label("Data startu zdarzenia:"), 0, 2);
        grid.add(dtstart, 1, 2);
        grid.add(new Label("Data końca zdarzenia:"), 0, 5);
        grid.add(dtend, 1, 5);
        grid.add(new Label("Czas startu zdarzenia"),0,3);
        grid.add(TimeStartH,2,4);
        grid.add(new Label(":"),3,4);
        grid.add(TimeStartM,4,4);
        grid.add(starth, 0, 4);
        grid.add(startm,1,4);
        grid.add(new Label("Czas końca zdarzenia"),0,6);
        grid.add(TimeEndH,2,7);
        grid.add(new Label(":"),3,7);
        grid.add(TimeEndM,4,7);
        grid.add(endh, 0, 7);
        grid.add(endm,1,7);
        grid.add(new Label("Czy ma być przypomnienie:"), 0, 8);
        grid.add(alarm,1,8);
        grid.add(new Label("O ile wcześnij przypomnieć:"), 0, 9);
        grid.add(czas,1,9);
        grid.add(new Label("O po jakim czasie ponowić przypomnienie:"), 0, 10);
        grid.add(trwanie,1,10);
        grid.add(new Label("Ile razy powtórzyć alarm:"), 0, 11);
        grid.add(powt,1,11);
        Node potwierdzenieButton = this.getDialogPane().lookupButton(potwierdzenieButtonType);
        potwierdzenieButton.setDisable(true);

        summary.textProperty().addListener((observable, oldValue, newValue) -> {
            potwierdzenieButton.setDisable(newValue.trim().isEmpty()); 
        });
        powt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(alarm.isSelected())
                potwierdzenieButton.setDisable(!isValidPowtorzenie(newValue)||!isValidCzas(czas.getText())||!isValidTrwanie(trwanie.getText(),newValue));
        });
        czas.textProperty().addListener((observable, oldValue, newValue) -> {
            if(alarm.isSelected())
                potwierdzenieButton.setDisable(!isValidPowtorzenie(powt.getText())||!isValidCzas(newValue)||!isValidTrwanie(trwanie.getText(),powt.getText()));
        });
        trwanie.textProperty().addListener((observable, oldValue, newValue) -> {
            if(alarm.isSelected())
                potwierdzenieButton.setDisable(!isValidPowtorzenie(powt.getText())||!isValidCzas(czas.getText())||!isValidTrwanie(newValue,powt.getText()));
        });
        
        this.getDialogPane().setContent(grid);

        Platform.runLater(() -> summary.requestFocus());
        this.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == potwierdzenieButtonType) {
                Calendar c1 = new GregorianCalendar();
                Calendar c2 = new GregorianCalendar();
                c1.set(Calendar.YEAR, dtstart.getValue().getYear());
                c1.set(Calendar.MONTH, dtstart.getValue().getMonthValue());
                c1.set(Calendar.DAY_OF_YEAR, dtstart.getValue().getDayOfYear());
                c1.set(Calendar.HOUR_OF_DAY, (int) starth.getValue());
                c1.set(Calendar.MINUTE, (int) starth.getValue());
                
                c2.set(Calendar.YEAR, dtend.getValue().getYear());
                c2.set(Calendar.MONTH, dtend.getValue().getMonthValue());
                c2.set(Calendar.DAY_OF_YEAR, dtend.getValue().getDayOfYear());
                c2.set(Calendar.HOUR_OF_DAY, (int) endh.getValue());
                c2.set(Calendar.MINUTE, (int) endm.getValue());
                Zdarzenie z = new Zdarzenie(c1,c2,summary.getText(), descript.getText()  );
                if(alarm.isSelected()){
                    Integer duration,repeat;
                    try {
                        duration = Integer.parseInt(trwanie.getText());
                    }
                    catch(NumberFormatException ex) {
                        duration = null;
                    }
                    try {
                        repeat = Integer.parseInt(powt.getText());
                    }
                    catch(NumberFormatException ex) {
                        repeat = null;
                    }
                    
                    
                    Przypomnienie a = new Przypomnienie(summary.getText(),repeat,z,Integer.parseInt(czas.getText()), duration);
                    z.setAlarm(a);
                }

                /*ZonedDateTime d = dtstart.getValue().atStartOfDay(ZoneId.systemDefault()).plusHours((long) starth.getValue());
                d.plusMinutes((long) startm.getValue());
                ZonedDateTime d2 = dtend.getValue().atStartOfDay(ZoneId.systemDefault()).plusHours((long) endh.getValue());
                d2.plusMinutes((long) endm.getValue());
                Calendar data = GregorianCalendar.from(d);
                Calendar data2 = GregorianCalendar.from(d2);*/
                return z;
            }
            return null;
        });


        
    }

    private boolean isValidPowtorzenie(String powt) {
        try {
            int test = Integer.parseInt(powt);
            if(test<0)
                return false;
        }
        catch(NumberFormatException ex) {
            return true;
        }
        return true;
    }

    private boolean isValidCzas(String czas) {
        try {
            int test = Integer.parseInt(czas);
            if(test<0)
                return false;
        }
        catch(NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private boolean isValidTrwanie(String trwanie, String powtorzenie) {
        try {
            int test2 = Integer.parseInt(powtorzenie);
            if(test2==0)
                return true;
            else if(test2>0) {
                int test = Integer.parseInt(trwanie);
                if(test<=0)
                    return false;
            }
        }
        catch(NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
   
}
