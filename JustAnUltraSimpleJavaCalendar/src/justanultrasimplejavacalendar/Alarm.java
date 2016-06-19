/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author lemi44
 */
public class Alarm {
    public static void sprawdzIUstawModel(KalendarzModel model) {
        
    }
    public void sprawdzIUstawZdarzenie(Zdarzenie zdarzenie) {
        if(zdarzenie.isAlarm()) {
            Timer timer = new Timer();
            PrzypomnienieTask task = new PrzypomnienieTask();
            timer.scheduleAtFixedRate(task, 0, 0);
        }
    }
    // TODO: Zrobić tego taska
    private class PrzypomnienieTask extends TimerTask {

        @Override
        public void run() {
            
        }
        
        public PrzypomnienieTask() {
            
        }
        
    }
}
