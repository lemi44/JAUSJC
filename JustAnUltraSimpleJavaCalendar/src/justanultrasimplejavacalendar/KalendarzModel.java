/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Xsior
 */
public class KalendarzModel {
    Collection<Zdarzenie> Kolekcja;

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
    public ObservableList<Zdarzenie> eventCheck(Date d)
    {
        ObservableList<Zdarzenie> lista = FXCollections.observableArrayList();
        Kolekcja.stream().filter((items) -> (items.containsDate(d))).forEach((item) -> {
            lista.add(item);
        });
        return lista;
    }
   
}
