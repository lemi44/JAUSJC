/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.util.Collection;
import java.util.HashSet;

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
}
