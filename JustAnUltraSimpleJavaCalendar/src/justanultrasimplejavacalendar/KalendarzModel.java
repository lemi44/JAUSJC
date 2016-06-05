/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 *
 * @author Xsior
 */
public class KalendarzModel {
    @FXML Collection<Zdarzenie> Kolekcja;

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
    public void delByDate(Date d)
    {
        for(Zdarzenie item: Kolekcja)
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
    public ObservableList<Zdarzenie> eventCheck(Date d)
    {
        ObservableList<Zdarzenie> lista = FXCollections.observableArrayList();
        Kolekcja.stream().filter((items) -> (items.containsDate(d))).forEach((item) -> {
            lista.add(item);
        });
        return lista;
    }
   public void toSql(sqlSerializer sql) throws ParseException
   {
            HashSet<Zdarzenie> z = sql.selectZdarzenia();

       for(Zdarzenie item : Kolekcja)
       {
           if(!z.contains(item))
           sql.insertZdarzenie(item);
       }
   }
    public void fromSql(sqlSerializer sql) throws ParseException
   {
            HashSet<Zdarzenie> z = sql.selectZdarzenia();

       for(Zdarzenie item : z)
       {
           if(!Kolekcja.contains(item))
           this.add(item);
       }
   }
    public void toXml(XMLSerializer xml){
        
            xml.saveKalendarz(this);
        }
            
    
    public void fromXml(XMLSerializer xml){
        
             readFromKalendarz(xml.loadKalendarz());
    }
    private void readFromKalendarz(KalendarzModel k){
        Kolekcja.addAll(k.getKolekcja());
    }
            
    
}
