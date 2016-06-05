/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Xsior
 */

public class Zdarzenie implements Serializable{
    private Date dtstamp;
    public Zdarzenie(){
        this.dtstamp = new Date(1990, 1, 1);
        this.dtstart = new Date(1990, 1, 1);
        this.dtend = new Date(1990, 1, 2);
        this.uid = " ";
        this.Summary = "Generic event";
        this.Description = " ";
    }
    public Zdarzenie(Date dtstamp, Date dtstart, Date dtend, String uid, String Summary, String Description) {
        this.dtstamp = dtstamp;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.uid = uid;
        this.Summary = Summary;
        this.Description = Description;
    }

    public Zdarzenie(Date dtstart, Date dtend, String Summary, String Description) {
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.Summary = Summary;
        this.Description = Description;
        this.dtstamp = new Date();
    }

    public Date getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(Date dtstamp) {
        this.dtstamp = dtstamp;
    }

    public Date getDtstart() {
        return dtstart;
    }

    public void setDtstart(Date dtstart) {
        this.dtstart = dtstart;
    }

    public Date getDtend() {
        return dtend;
    }

    public void setDtend(Date dtend) {
        this.dtend = dtend;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
    public Boolean containsDate(Date d) {
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
    private Date dtstart;
    private Date dtend;
    private String uid;
    private String Summary;
    private String Description;
}
