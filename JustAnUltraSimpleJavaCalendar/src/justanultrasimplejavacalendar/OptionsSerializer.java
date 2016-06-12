/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author Xsior
 */
public class OptionsSerializer {
    private static final String SERIALIZED_FILE_NAME = "options";
    public final static char CR  = (char) 0x0D;
    public final static char LF  = (char) 0x0A; 
    public final static String CRLF  = "" + CR + LF;
    
    public void save(Boolean b, String s){
    try(BufferedWriter writer = new BufferedWriter(new FileWriter(SERIALIZED_FILE_NAME))) {
        writer.write(b.toString());
        writer.write(CRLF);
        writer.write(s);
        
    }
    catch(FileNotFoundException fileNotFound) {
            System.err.println("ERROR: While Creating or Opening the File kalendarz.ics");
        } catch (IOException ex) {
            Logger.getLogger(ICalendarSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Pair<Boolean,String> load()
    {
        Boolean b=null;
        String s=null;
        try(BufferedReader reader = new BufferedReader(new FileReader(SERIALIZED_FILE_NAME))){
            String buffer = reader.readLine();
            if(buffer.contains("true"))
            {
                b=true;
            }
            else if(buffer.contains("false"))
            {
                b=false;
            }
            else
            {
               b=false;
            }
            buffer = reader.readLine();
            s = buffer;
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: Options file not found");
            b=false;
            s="";
        } catch (IOException ex) {
            Logger.getLogger(OptionsSerializer.class.getName()).log(Level.SEVERE, null, ex);
            b=false;
            s="";
        }
        
        return new Pair<>(b,s); 
    }
}
