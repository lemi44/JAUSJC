/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.util.UUID;
import javafx.util.Duration;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Xsior
 */
@XmlRootElement(name = "Przypomnienie")
@XmlType(propOrder = { "opis", "trigger", "czasTrwania", "powtorzenia", "zdarzenie"})
public class Przypomnienie {
    private String opis;
    private int trigger;
    private int czasTrwania;
    private Zdarzenie zdarzenie;
    
    public Przypomnienie(String opis, int powtorzenia, Zdarzenie zdarzenie, int trigger, int czas) {
        this.opis = opis;
        this.powtorzenia = powtorzenia;
        this.zdarzenie = zdarzenie;
        this.trigger = trigger;
        this.czasTrwania = czas;
    }
    private int powtorzenia;

    public Przypomnienie() {
        opis = "";
        trigger = 30;
        czasTrwania = 15;
        powtorzenia=5;
    }
    @XmlElement(name = "opis")
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    @XmlElement(name = "trigger")
    public int getTrigger() {
        return trigger;
    }

    public void setTrigger(int trigger) {
        this.trigger = trigger;
    }
    @XmlElement(name = "czastrwania")
    public int getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(int czasTrwania) {
        this.czasTrwania = czasTrwania;
    }
    @XmlElement(name = "powtorzenia")
    public int getPowtorzenia() {
        return powtorzenia;
    }

    public void setPowtorzenia(int powtorzenia) {
        this.powtorzenia = powtorzenia;
    }
    @XmlElement(name = "zdarzenie")
    public Zdarzenie getZdarzenie() {
        return zdarzenie;
    }

    public void setZdarzenie(Zdarzenie zdarzenie) {
        this.zdarzenie = zdarzenie;
    }
    public String getIDZdarzenie() {
        return this.zdarzenie.getUid();
    }
    
}
