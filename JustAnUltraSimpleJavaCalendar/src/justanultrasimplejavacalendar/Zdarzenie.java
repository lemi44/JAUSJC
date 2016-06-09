/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;


/**
 *
 * @author Xsior
 */

public class Zdarzenie implements Serializable{
    private Calendar dtstamp;
    public Zdarzenie(){
        this.dtstamp = new GregorianCalendar(1990, 1, 1);
        this.dtstart = new GregorianCalendar(1990, 1, 1);
        this.dtend = new GregorianCalendar(1990, 1, 2);
        this.uid = UUID.randomUUID();
        this.Summary = "Generic event";
        this.Description = " ";
    }
    public Zdarzenie(Calendar dtstamp, Calendar dtstart, Calendar dtend, String uid, String Summary, String Description) {
        this.dtstamp = dtstamp;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.uid = UUID.fromString(uid);
        this.Summary = Summary;
        this.Description = Description;
    }

    public Zdarzenie(Calendar dtstart, Calendar dtend, String Summary, String Description) {
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.Summary = Summary;
        this.Description = Description;
        this.dtstamp = new GregorianCalendar();
        this.uid = UUID.randomUUID();
    }

    public Calendar getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(Calendar dtstamp) {
        this.dtstamp = dtstamp;
    }

    public Calendar getDtstart() {
        return dtstart;
    }

    public void setDtstart(Calendar dtstart) {
        this.dtstart = dtstart;
    }

    public Calendar getDtend() {
        return dtend;
    }

    public void setDtend(Calendar dtend) {
        this.dtend = dtend;
    }

    public String getUid() {
        return uid.toString();
    }

    public void setUid(String uid) {
        this.uid = UUID.fromString(uid);
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public String getDescription() {
        return Description;
    }
    public Boolean containsDate(Calendar d) {
        if(dtstart.before(d) && d.before(dtend))
        {
            return true; 
        }
        else
            return false;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }
    private Calendar dtstart;
    private Calendar dtend;
    private UUID uid;
    private String Summary;
    private String Description;
}
