/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;

/**
 *
 * @author Xsior
 */


public class XMLSerializer {
    private static final String SERIALIZED_FILE_NAME="kalendarz.xml";
    XMLEncoder encoder;
    XMLDecoder decoder;
    public XMLSerializer() {
        

    }
    public void saveKalendarz(KalendarzModel z){
        
        try{
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
        encoder.writeObject(z);
    }
    public KalendarzModel loadKalendarz()
    {
       
       try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
            } 
       catch (FileNotFoundException e) {
			System.out.println("ERROR: File dvd.xml not found");
            } 
       KalendarzModel k = (KalendarzModel)decoder.readObject();
       return k;
    }
    public void close(){
        encoder.close();
    }
}
