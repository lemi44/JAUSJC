/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
@XmlRootElement(name = "Zdarzenie")
@XmlType(propOrder = { "dtstamp", "dtstart", "dtend", "uid", "summary", "description", "alarm" })

public class Zdarzenie implements Serializable{
    private Calendar dtstamp;
    public Zdarzenie(){
        this.dtstamp = new GregorianCalendar(1990, 1, 1);
        this.dtstart = new GregorianCalendar(1990, 1, 1);
        this.dtend = new GregorianCalendar(1990, 1, 2);
        this.uid = UUID.randomUUID();
        this.Summary = "Generic event";
        this.Description = " ";
        alarm = null;
    }
    public Zdarzenie(Calendar dtstamp, Calendar dtstart, Calendar dtend, String uid, String Summary, String Description) {
        this.dtstamp = dtstamp;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.uid = UUID.fromString(uid);
        this.Summary = Summary;
        this.Description = Description;
        alarm = null;
    }

    public Zdarzenie(Calendar dtstart, Calendar dtend, String Summary, String Description) {
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.Summary = Summary;
        this.Description = Description;
        this.dtstamp = new GregorianCalendar();
        this.uid = UUID.randomUUID();
        alarm = null;
    }
    @XmlElement(name = "dtstamp")
    public Calendar getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(Calendar dtstamp) {
        this.dtstamp = dtstamp;
    }
    @XmlElement(name = "dtstart")
    public Calendar getDtstart() {
        return dtstart;
    }
    @XmlTransient
    public Date getDateStart() {
        return dtstart.getTime();
    }
    
    public void setDtstart(Calendar dtstart) {
        this.dtstart = dtstart;
    }
    
    public void setDateStart(Date dtstart) {
        this.dtstart.setTime(dtstart);
    }
    
    @XmlElement(name = "dtend")
    public Calendar getDtend() {
        return dtend;
    }
    @XmlTransient
    public Date getDateEnd() {
        return dtend.getTime();
    }

    public void setDtend(Calendar dtend) {
        this.dtend = dtend;
    }
    
    public void setDateEnd(Date dtend) {
        this.dtend.setTime(dtend);
    }
    
    @XmlElement(name = "uid")
    public String getUid() {
        return uid.toString();
    }

    public void setUid(String uid) {
        this.uid = UUID.fromString(uid);
    }
    @XmlElement(name = "summary")
    public String getSummary() {
        return Summary;
    }
    
    public void setSummary(String Summary) {
        this.Summary = Summary;
    }
    @XmlElement(name = "description")
    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
   @XmlElement(name = "alarm")
    public Przypomnienie getAlarm() {
        return alarm;
    }
    
    public void setAlarm(Przypomnienie alarm) {
        this.alarm = alarm;
    }
    
    public boolean isAlarm() {
        if(alarm==null)
            return false;
        else if(alarm instanceof Przypomnienie)
            return true;
        else
            return false;
    }
    
    public Boolean containsDate(Calendar d) {
        if(dtstart.before(d) && d.before(dtend))
        {
            return true; 
        }
        else
            return false;
    }
    
    private Calendar dtstart;
    private Calendar dtend;
    private UUID uid;
    private String Summary;
    private String Description;
    /*private Duration alarm;
    private boolean isAlarm;*/
    private Przypomnienie alarm;
}
