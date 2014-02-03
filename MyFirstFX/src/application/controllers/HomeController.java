package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import application.VistaNavigator;

public class HomeController implements Initializable {
	@FXML
    AnchorPane home;
	
	@FXML
    Button gotoTests;
    @FXML
    Button gotoQuestions;
    
    /**
    * Event handler fired when the user requests a tests vista.
    *
    * @param event the event that triggered the handler.
    */
    @FXML
    void gotoTests(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.TESTS);
    }
    
    /**
     * Event handler fired when the user requests a questions vista.
     *
     * @param event the event that triggered the handler.
     */
     @FXML
     void gotoQuestions(ActionEvent event) {
     	VistaNavigator.loadVista(VistaNavigator.QUESTIONS);
     }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
