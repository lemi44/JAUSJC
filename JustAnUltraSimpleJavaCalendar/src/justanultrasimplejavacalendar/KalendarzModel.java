/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.text.ParseException;
import java.util.Collection;
import java.util.Calendar;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Xsior
 */
@XmlRootElement(namespace = "justanultrasimplejavacalendar")


public class KalendarzModel {
    @XmlElementWrapper(name = "KalendarzModel")
    // XmlElement sets the name of the entities
    @XmlElement(name = "Zdarzenie")
    Collection<Zdarzenie> Kolekcja;
    
    public Collection<Zdarzenie> getKolekcja() {
        return Kolekcja;
    }

    public void setKolekcja(Collection<Zdarzenie> Kolekcja) {
        this.Kolekcja = Kolekcja;
    }

    public KalendarzModel() {
        this.Kolekcja = new HashSet<Zdarzenie>();
        
    }
    
    public void add(Zdarzenie z)
    {
        Kolekcja.add(z);
    }
    public void del(Zdarzenie z)
    {
        Kolekcja.remove(z);
    }
    public void delByDate(Calendar d)
    {
        HashSet<Zdarzenie> tmp =  new HashSet<Zdarzenie>(Kolekcja);
        for(Zdarzenie item: tmp)
        {
            if(!item.getDtend().after(d))
            {
                this.del(item);
            }
        }
    }
    public void addSet(HashSet<Zdarzenie> h)
    {
        Kolekcja.addAll(h);
    }
    public ObservableList<Zdarzenie> eventCheck(Calendar d)
    {
        ObservableList<Zdarzenie> lista = FXCollections.observableArrayList();
        Kolekcja.stream().filter((items) -> (items.containsDate(d))).forEach((item) -> {
            lista.add(item);
        });
        return lista;
    }
   public void toSql(SQLSerializer sql) throws ParseException
   {
            HashSet<Zdarzenie> z = sql.selectZdarzenia();

       for(Zdarzenie item : Kolekcja)
       {
           if(!z.contains(item))
           sql.insertZdarzenie(item);
       }
   }
    public void fromSql(SQLSerializer sql) throws ParseException
   {
            Kolekcja = sql.selectZdarzenia();

   }
    public void toXml(XMLSerializer xml){
        
            xml.saveKalendarz(this);
        }
            
    
    public void fromXml(XMLSerializer xml){
        
              Kolekcja=xml.loadKalendarz().getKolekcja();
    }
            
    
}
