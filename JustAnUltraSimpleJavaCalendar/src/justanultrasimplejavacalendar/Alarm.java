/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lemi44
 */
public class Alarm {
    private ObservableList<Przypomnienie> listaPrzypomnien;
    private static Alarm instance;
    private Alarm() {
        listaPrzypomnien = FXCollections.observableArrayList();
    }
    
    public static Alarm getInstance() {
        if(instance==null)
            instance=new Alarm();
        return instance;
    }
    
    public void sprawdzIUstawModel(KalendarzModel model) {
        ObservableList<Zdarzenie> lista = FXCollections.observableArrayList();
        for(Zdarzenie zd : model.getKolekcja())
                lista.add(zd);
        for(Zdarzenie zd : lista) {
            sprawdzIUstawZdarzenie(zd);
        } 
    }
    private void sprawdzIUstawZdarzenie(Zdarzenie zdarzenie) {
        if(zdarzenie.isAlarm()) {
            if(!listaPrzypomnien.contains(zdarzenie.getAlarm())) {
                listaPrzypomnien.add(zdarzenie.getAlarm());
                Timer timer = new Timer();
                PrzypomnienieTask task = new PrzypomnienieTask(zdarzenie.getAlarm());
                // TODO: Ustaw poprawnie to
                timer.scheduleAtFixedRate(task, 0, 0);
            }
            
        }
    }
    // TODO: Zrobić tego taska aby wyświetlał Alert
    private class PrzypomnienieTask extends TimerTask {
        private Przypomnienie przypomnienie;

        @Override
        public void run() {
            
        }
        
        public PrzypomnienieTask(Przypomnienie p) {
            przypomnienie = p;
        }
        
    }
}
