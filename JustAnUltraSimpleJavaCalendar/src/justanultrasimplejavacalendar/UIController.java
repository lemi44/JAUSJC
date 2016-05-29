/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justanultrasimplejavacalendar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Michał
 */
public class UIController implements Initializable {
    
    @FXML private MenuBar menuBar;
    @FXML private MenuItem wyjscieMenuItem;

    @FXML
    private void handleDodajAction(ActionEvent event) {
        
    }
    
    @FXML
    private void handleUsunDialogAction() {
        
    }
    
    @FXML
    private void handleFiltrujDialogAction() {
        
    }
    
    @FXML
    private void handleImportICalendarAction() {
        
    }
    
    @FXML
    private void handleImportXMLAction() {
        
    }
    
    @FXML
    private void handleImportDatabaseAction() {
        
    }
    
    @FXML
    private void handleExportICalendarAction() {
        
    }
    
    @FXML
    private void handleExportXMLAction() {
        
    }
    
    @FXML
    private void handleExportDatabaseAction() {
        
    }
    
    @FXML
    private void handleUstawieniaAction() {
        
    }
    
    @FXML
    private void handleWyjscieAction() {
        Platform.exit();
    }
    
    @FXML
    private void handleAboutAction() {
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wyjscieMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Q"));
    }     
    
}
