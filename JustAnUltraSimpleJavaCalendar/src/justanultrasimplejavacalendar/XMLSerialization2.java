/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Xsior
 */
public class XMLSerialization2 {
    JAXBContext context;
    Marshaller m;
    XMLSerialization2(){
        try {
            context = JAXBContext.newInstance(KalendarzModel.class);
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException ex) {
            Logger.getLogger(XMLSerialization2.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    public void saveKalendarz(KalendarzModel k)
    {
        try {
            m.marshal(k, new File("./Kalendarz2.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(XMLSerialization2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public KalendarzModel loadKalendarz(){
        try {
            Unmarshaller um = context.createUnmarshaller();
            try {
                KalendarzModel model2 = (KalendarzModel) um.unmarshal(new FileReader("./Kalendarz2.xml"));
                return model2;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(XMLSerialization2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (JAXBException ex) {
            Logger.getLogger(XMLSerialization2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
