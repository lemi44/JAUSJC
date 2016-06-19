/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
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
            if(!listaPrzypomnien.contains(zdarzenie.getAlarm())&&zdarzenie.getDateEnd().before(new Date())) {
                listaPrzypomnien.add(zdarzenie.getAlarm());
                if(zdarzenie.getDateEnd().after(new Date()))
                {
                    zdarzenie.getAlarm().setPowtorzenia(0);
                }
                Timer timer = new Timer();
                PrzypomnienieTask task = new PrzypomnienieTask(zdarzenie.getAlarm(), zdarzenie);
                // TODO: Ustaw poprawnie to
                    Calendar d = new GregorianCalendar();
                    d.setTime(zdarzenie.getDateStart());
                    d.add(Calendar.MINUTE, -(zdarzenie.getAlarm().getTrigger()));
                    timer.schedule(task, d.getTime(), zdarzenie.getAlarm().getCzasTrwania()*1000*60); 

            }
            
        }
    }
    // TODO: Zrobić tego taska aby wyświetlał Alert
    private class PrzypomnienieTask extends TimerTask {
        private Przypomnienie przypomnienie;
        private Zdarzenie zdarzenie;
        @Override
        public void run() {
            
           if(przypomnienie.getPowtorzenia()>0&&zdarzenie.getDateEnd().before(new Date())){
               Platform.runLater(() -> {
               PrzypomnienieAlert t = new PrzypomnienieAlert(zdarzenie);
               przypomnienie.setPowtorzenia(przypomnienie.getPowtorzenia()-1);
               });
           }else{
        this.cancel();
           }
        }

        @Override
        public boolean cancel() {
            return super.cancel(); //To change body of generated methods, choose Tools | Templates.
        }
        
        public PrzypomnienieTask(Przypomnienie p, Zdarzenie z) {
            przypomnienie = p;
            zdarzenie = z;
            Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                cancel();
            }
           });
        }
        
    }
}
