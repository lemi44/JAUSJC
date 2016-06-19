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
@XmlType(propOrder = { "opis", "trigger", "czasTrwania", "powtorzenia"})
public class Przypomnienie {
    private String opis;
    private Duration trigger = Duration.ZERO;
    private Duration czasTrwania = Duration.ZERO;
    private Zdarzenie zdarzenie;
    
    public Przypomnienie(String opis, int powtorzenia, Zdarzenie zdarzenie, int trigger, int czas) {
        this.opis = opis;
        this.powtorzenia = powtorzenia;
        this.zdarzenie = zdarzenie;
        this.trigger.add(Duration.minutes(trigger));
        this.czasTrwania.add(Duration.minutes(czas));
    }
    private int powtorzenia;

    public Przypomnienie() {
        opis = "";
        trigger.add(Duration.minutes(30));
        czasTrwania.add(Duration.minutes(15));
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
    public Duration getTrigger() {
        return trigger;
    }

    public void setTrigger(Duration trigger) {
        this.trigger = trigger;
    }
    @XmlElement(name = "czastrwania")
    public Duration getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(Duration czasTrwania) {
        this.czasTrwania = czasTrwania;
    }
    @XmlElement(name = "powtorzenia")
    public int getPowtorzenia() {
        return powtorzenia;
    }

    public void setPowtorzenia(int powtorzenia) {
        this.powtorzenia = powtorzenia;
    }
    @XmlTransient
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
