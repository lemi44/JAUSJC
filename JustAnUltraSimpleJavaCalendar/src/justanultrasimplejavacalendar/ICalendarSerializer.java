/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.*;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.*;
import javafx.util.Duration;

/**
 *
 * @author lemi44
 */
public class ICalendarSerializer {

    private String SERIALIZED_FILE_NAME = "kalendarz.ics";
    public final static char CR  = (char) 0x0D;
    public final static char LF  = (char) 0x0A; 
    public final static String CRLF  = "" + CR + LF;
    private final DateFormat fmtDate = new SimpleDateFormat("yyyyMMdd");
    private final DateFormat fmtTime = new SimpleDateFormat("HHmmss");
    public void setDir(String dir){
        SERIALIZED_FILE_NAME = dir + "/" + SERIALIZED_FILE_NAME;
    }
    public void setName(String n)
    {
        SERIALIZED_FILE_NAME = n;
    }
    public void saveKalendarz(KalendarzModel z) throws FileNotFoundException, IOException  {
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(SERIALIZED_FILE_NAME))) {
            writer.write("BEGIN:VCALENDAR");
            writer.write(CRLF);
            writer.write("VERSION:2.0");
            writer.write(CRLF);
            writer.write("PRODID:-//lemi44//JAUSJC//EN");
            writer.write(CRLF);
            for(Zdarzenie event : z.getKolekcja()) {
                writer.write("BEGIN:VEVENT");
                writer.write(CRLF);
                writer.write("UID:");
                writer.write(event.getUid());
                writer.write(CRLF);
                writer.write("DTSTAMP:");
                writer.write(fmtDate.format(event.getDtstamp().getTime()).concat("T").concat(fmtTime.format(event.getDtstamp().getTime())).concat("Z"));
                writer.write(CRLF);
                writer.write("DTSTART:");
                writer.write(fmtDate.format(event.getDtstart().getTime()).concat("T").concat(fmtTime.format(event.getDtstamp().getTime())).concat("Z"));
                writer.write(CRLF);
                writer.write("DTEND:");
                writer.write(fmtDate.format(event.getDtend().getTime()).concat("T").concat(fmtTime.format(event.getDtstamp().getTime())).concat("Z"));
                writer.write(CRLF);
                writer.write("SUMMARY:");
                writer.write(event.getSummary());
                writer.write(CRLF);
                if(!event.getDescription().isEmpty()) {
                    writer.write("DESCRIPTION:");
                    writer.write(event.getDescription());
                    writer.write(CRLF);
                }   
                if(event.isAlarm()==true) {
                    writer.write("BEGIN:VALARM");
                    writer.write(CRLF);
                    writer.write("ACTION:DISPLAY");
                    writer.write(CRLF);
                    writer.write("DESCRIPTION:");
                    writer.write(event.getSummary());
                    writer.write(CRLF);
                    writer.write("TRIGGER:-PT");
                    writer.write(String.valueOf(event.getAlarm().getTrigger()));
                    writer.write("M");
                    writer.write(CRLF);
                    writer.write("REPEAT:");
                    writer.write(String.valueOf(event.getAlarm().getPowtorzenia()));
                    writer.write(CRLF);
                    writer.write("DURATION:PT");
                    writer.write(String.valueOf(event.getAlarm().getCzasTrwania()));
                    writer.write("M");
                    writer.write(CRLF);
                    writer.write("UID:");
                    writer.write(event.getUid());
                    writer.write(CRLF);
                    writer.write("END:VALARM");
                    writer.write(CRLF);
                }   
                writer.write("END:VEVENT");
                writer.write(CRLF);
            }
            writer.write("END:VCALENDAR");
            writer.write(CRLF);
        } catch(FileNotFoundException fileNotFound) {
            Logger.getLogger(ICalendarSerializer.class.getName()).log(Level.SEVERE, null, fileNotFound);
            throw fileNotFound;
        } catch (IOException ex) {
            Logger.getLogger(ICalendarSerializer.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    public KalendarzModel loadKalendarz() throws FileNotFoundException, IOException, ParseException {
       
       try(BufferedReader reader = new BufferedReader(new FileReader(SERIALIZED_FILE_NAME))) {
           boolean loop = true;
           KalendarzModel k = null;
           Zdarzenie ev = null;
           Przypomnienie a = null;
           while(loop) {
               String buffer = reader.readLine();
               boolean x = false;
               String[] propertyValuePair = buffer.split(":");
               switch(propertyValuePair[0])
               {
                   case "BEGIN":
                       switch(propertyValuePair[1])
                       {
                           case "VCALENDAR":
                               k = new KalendarzModel();
                               break;
                           case "VEVENT":
                               ev = new Zdarzenie();
                               break;
                           case "VALARM":
                               a = new Przypomnienie();
                               x=true;
                               break;
                       }                       
                       break;
                   case "END":                       
                       switch(propertyValuePair[1])
                       {
                           case "VCALENDAR":
                               loop = false;
                               break;
                           case "VEVENT":
                               k.add(ev);
                               break;
                           case "VALARM":
                               ev.setAlarm(a);
                               x=false;
                               break;
                       }
                       break;
                   case "UID":
                       if(x==true){
                           a.setOpis(propertyValuePair[1]);
                       }else
                       ev.setUid(propertyValuePair[1]);
                       
                       break;
                   case "DTSTAMP":
                   {
                       String[] dateTimePair = propertyValuePair[1].split("T");
                       dateTimePair[1] = dateTimePair[1].substring(0, dateTimePair[1].length()-1);
                       Calendar cal = new GregorianCalendar();
                       Calendar tmpCal = new GregorianCalendar();
                       cal.setTime(fmtDate.parse(dateTimePair[0]));
                       tmpCal.setTime(fmtTime.parse(dateTimePair[1]));
                       cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                               cal.get(Calendar.DAY_OF_MONTH),
                               tmpCal.get(Calendar.HOUR),
                               tmpCal.get(Calendar.MINUTE),
                               tmpCal.get(Calendar.SECOND));
                       ev.setDtstamp(cal);
                   }
                       break;
                   case "DTSTART":
                       {
                       String[] dateTimePair = propertyValuePair[1].split("T");
                       dateTimePair[1] = dateTimePair[1].substring(0, dateTimePair[1].length()-1);
                       Calendar cal = new GregorianCalendar();
                       Calendar tmpCal = new GregorianCalendar();
                       cal.setTime(fmtDate.parse(dateTimePair[0]));
                       tmpCal.setTime(fmtTime.parse(dateTimePair[1]));
                       cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                               cal.get(Calendar.DAY_OF_MONTH),
                               tmpCal.get(Calendar.HOUR),
                               tmpCal.get(Calendar.MINUTE),
                               tmpCal.get(Calendar.SECOND));
                       ev.setDtstart(cal);
                   }
                       break;
                   case "DTEND":
                       {
                       String[] dateTimePair = propertyValuePair[1].split("T");
                       dateTimePair[1] = dateTimePair[1].substring(0, dateTimePair[1].length()-1);
                       Calendar cal = new GregorianCalendar();
                       Calendar tmpCal = new GregorianCalendar();
                       cal.setTime(fmtDate.parse(dateTimePair[0]));
                       tmpCal.setTime(fmtTime.parse(dateTimePair[1]));
                       cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                               cal.get(Calendar.DAY_OF_MONTH),
                               tmpCal.get(Calendar.HOUR),
                               tmpCal.get(Calendar.MINUTE),
                               tmpCal.get(Calendar.SECOND));
                       ev.setDtend(cal);
                   }
                       break;
                   case "SUMMARY":
                       ev.setSummary(propertyValuePair[1]);

                       break;
                   case "DESCRIPTION":
                       if(x==true){
                            a.setOpis(propertyValuePair[1]);
                       }else
                       ev.setDescription(propertyValuePair[1]);

                       break;
                   case "TRIGGER":
                       propertyValuePair[1] = propertyValuePair[1].replaceAll("\\D+","");
                       a.setTrigger(Integer.parseInt(propertyValuePair[1]));
                       break;
                   case "REPEAT":
                       a.setPowtorzenia(Integer.parseInt(propertyValuePair[1]));
                       break;
                   case "DURATION":
                       propertyValuePair[1] = propertyValuePair[1].replaceAll("\\D+","");
                       a.setCzasTrwania(Integer.parseInt(propertyValuePair[1]));
                       break; 
               }
           }
           return k;
       } catch (FileNotFoundException ex) {
           Logger.getLogger(ICalendarSerializer.class.getName()).log(Level.SEVERE, null, ex);
           throw ex;
       } catch (IOException | ParseException ex) {
           Logger.getLogger(ICalendarSerializer.class.getName()).log(Level.SEVERE, null, ex);
           throw ex;
       }
    }
    
}
