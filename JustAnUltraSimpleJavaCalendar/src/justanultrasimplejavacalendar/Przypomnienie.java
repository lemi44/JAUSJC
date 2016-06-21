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
@XmlType(propOrder = { "opis", "trigger", "czasTrwania", "powtorzenia", "uid"})
public class Przypomnienie {
    private String opis;
    private int trigger;
    private Integer czasTrwania;
    private Zdarzenie zdarzenie;
    private String uid;

    @XmlElement(name = "uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public Przypomnienie(String opis, Integer powtorzenia, Zdarzenie zdarzenie, int trigger, Integer czas) {
        this.opis = opis;
        this.powtorzenia = powtorzenia;
        if(this.powtorzenia!=null)
            if(this.powtorzenia==0)
                this.powtorzenia=null;
        this.zdarzenie = zdarzenie;
        this.trigger = trigger;
        this.czasTrwania = czas;
        this.uid = zdarzenie.getUid();
    }
    private Integer powtorzenia;

    public Przypomnienie() {
        opis = "";
        trigger = 30;
        czasTrwania = null;
        powtorzenia = null;
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
    public Integer getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(Integer czasTrwania) {
        this.czasTrwania = czasTrwania;
    }
    @XmlElement(name = "powtorzenia")
    public Integer getPowtorzenia() {
        return powtorzenia;
    }

    public void setPowtorzenia(Integer powtorzenia) {
        this.powtorzenia = powtorzenia;
    }
    @XmlTransient
    public Zdarzenie getZdarzenie() {
        return zdarzenie;
    }

    public void setZdarzenie(Zdarzenie zdarzenie) {
        this.zdarzenie = zdarzenie;
    }

    
}
