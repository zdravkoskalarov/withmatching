package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import application.VistaNavigator;
import application.dao.MySQLDAO;
import application.dto.Question;
import application.dto.QuestionsList;
import application.dto.Test;
import application.dto.TestsList;

public class TestsController implements Initializable {
	@FXML
    AnchorPane tests;
	
	//Buttons
	@FXML
	Button addBtn;
	
	@FXML
	Button deleteBtn;
	@FXML
	Button bindBtn;
	@FXML
	Button playBtn;
	
	//Text Field
	@FXML
	TextField nameFld;
	
	//Table properties
	@FXML
	TableView<Test> testsTable;
	
	@FXML
	TableColumn<Test, Integer> id;
	@FXML
	TableColumn<Test, String> name;

	
	ObservableList<Test> data;
	
	public static int selectedTestId = 0;
	
	@FXML
    void addTest(ActionEvent event) {
    	if (!nameFld.getText().equals("")) {
    		//if the fields are not empty
    		
    		try {
    			Test t = new Test();
        		t.setName(nameFld.getText());
        		
				MySQLDAO dao = new MySQLDAO();
				dao.insertTest(t);
				data.add(t);
				
				//clear fields
				nameFld.clear();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
	
	@FXML
	void deleteTest(ActionEvent event) {
		try {
			Test t = testsTable.getSelectionModel().getSelectedItem();

			MySQLDAO dao = new MySQLDAO();
			dao.deleteTest(t);
			data.remove(t);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }
	
	@FXML
	void bindTests(ActionEvent event) {
		try {
			
			VistaNavigator.loadVista(VistaNavigator.BINDING);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }
	
	@FXML
	void playTests(ActionEvent event) {
		try {
			selectedTestId = testsTable.getSelectionModel().getSelectedItem().getId();
			VistaNavigator.loadVista(VistaNavigator.PLAY);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }
	
	@FXML
	public void editName(CellEditEvent<Test, String> editor) {
        Test t = (Test) editor.getTableView().getItems().get(editor.getTablePosition().getRow());
        
        t.setName(editor.getNewValue());
        try {
			MySQLDAO dao = new MySQLDAO();
			
			dao.updateTest(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	public void goHome()
	{
		try
		{
			VistaNavigator.loadVista(VistaNavigator.HOME);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void goQuestions()
	{
		try
		{
			VistaNavigator.loadVista(VistaNavigator.QUESTIONS);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		MySQLDAO dao;
		try {
			
			dao = new MySQLDAO();
			TestsList tList = dao.loadAllTests();
			data = FXCollections.observableList(tList);
			
			id.setCellValueFactory(new PropertyValueFactory<Test, Integer>("id"));
			
			name.setCellValueFactory(new PropertyValueFactory<Test, String>("name"));
			name.setCellFactory(TextFieldTableCell.<Test>forTableColumn());
			
			testsTable.setItems(data);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	
}
