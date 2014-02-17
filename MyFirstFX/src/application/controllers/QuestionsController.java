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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import application.VistaNavigator;
import application.dao.MySQLDAO;
import application.dto.Question;
import application.dto.QuestionsList;

public class QuestionsController implements Initializable {
	@FXML
	AnchorPane questions;
	
	//Buttons
	@FXML
	Button addBtn;
	
	@FXML
	Button deleteBtn;
	
	//TextFields
	@FXML
	TextField bodyFld;
	
	@FXML
	TextField answerFld;
	
	//Table properties
	@FXML
	TableView<Question> questionsTable;
	
	@FXML
	TableColumn<Question, String> id;
	@FXML
	TableColumn<Question, String> body;
	@FXML
	TableColumn<Question, String> answer;
	
	ObservableList<Question> data;
	
	
	@FXML
    void addQuestion(ActionEvent event) {
    	if (!bodyFld.getText().equals("") && !answerFld.getText().equals("")) {
    		//if the fields are not empty
    		
    		try {
    			Question q = new Question();
        		
        		q.setBody(bodyFld.getText());
        		q.setAnswer(answerFld.getText());
        		
    			
				MySQLDAO dao = new MySQLDAO();
				
				dao.insertQuestion(q);
				
				data.add(q);
				
				//clear fields
				bodyFld.clear();
				answerFld.clear();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
	
	@FXML
	void deleteQuestion(ActionEvent event) {
		try {
			Question q = questionsTable.getSelectionModel().getSelectedItem();

			MySQLDAO dao = new MySQLDAO();
			
			dao.deleteQuestion(q);
			
			data.remove(q);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }
	
	@FXML
	public void editBody(CellEditEvent<Question, String> t) {
        Question q = (Question) t.getTableView().getItems().get(t.getTablePosition().getRow());
        
        q.setBody(t.getNewValue());
        try {
			MySQLDAO dao = new MySQLDAO();
			
			dao.updateQuestion(q);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	@FXML
	public void editAnswer(CellEditEvent<Question, String> t) {
        Question q = (Question) t.getTableView().getItems().get(t.getTablePosition().getRow());
        System.out.println("Editing answer");
        q.setAnswer(t.getNewValue());
        try {
			MySQLDAO dao = new MySQLDAO();
			
			dao.updateQuestion(q);
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
	
	public void goTests()
	{
		try
		{
			VistaNavigator.loadVista(VistaNavigator.TESTS);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void exitProgram()
	{
		try
		{
			System.exit(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		MySQLDAO dao;
		try {
			
			dao = new MySQLDAO();
			QuestionsList qList = dao.loadAllQuestions();
			data = FXCollections.observableList(qList);
			
			
			id.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
			
			
			body.setCellValueFactory(new PropertyValueFactory<Question, String>("body"));
			body.setCellFactory(TextFieldTableCell.<Question>forTableColumn());
			
			answer.setCellValueFactory(new PropertyValueFactory<Question, String>("answer"));
			answer.setCellFactory(TextFieldTableCell.<Question>forTableColumn());
			
			questionsTable.setItems(data);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		
	}
}
