package application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import application.VistaNavigator;
import application.dao.MySQLDAO;
import application.dto.CheckableQuestion;
import application.dto.QuestionsList;

public class PlayController {
	@FXML
	AnchorPane playing;
	
	//Buttons
	@FXML
	Button resetPlay;
	
	@FXML
	
	Button submit;
	
	//Table Properties
	@FXML
	TableView<CheckableQuestion> questionsTable;
	
	@FXML
	TableColumn<CheckableQuestion, String> body;
	@FXML
	TableColumn<CheckableQuestion, String> answer;
	@FXML
	TableColumn<CheckableQuestion, Boolean> result;
	
	@FXML
	TableView<CheckableQuestion> optionsTable;
	@FXML
	TableColumn<CheckableQuestion, String> options;
	
	ObservableList<CheckableQuestion> data;
	ObservableList<CheckableQuestion> optionsData;
	CheckableQuestion probe;
	
	
	//semi drag and drop implementation
	public void getSelectedAnswer(MouseEvent event)
	{
		CheckableQuestion privateProbe = new CheckableQuestion(optionsTable.getSelectionModel().getSelectedItem());
		probe = privateProbe;
		System.out.println("Item selected : " + probe.getAnswer());
		event.consume();
	}
	
	public void selectAnswer(CellEditEvent<CheckableQuestion, String> event)
	{
		System.out.println("Answer dropped");
		
		System.out.println("before: " + event.getRowValue().getGuess());
		event.getRowValue().setGuess(probe.getAnswer());
		System.out.println("after: " + event.getRowValue().getGuess());
		System.out.println("right table option: " + optionsTable.getSelectionModel().getSelectedItem().getGuess());
		
		refreshAnswerColumn();
		refreshOptionsColumn();
		
		event.consume();
		
	}
	
	public void refreshAnswerColumn() {
		questionsTable.getColumns().get(0).setVisible(false);
		questionsTable.getColumns().get(0).setVisible(true);
	}
	
	public void refreshOptionsColumn() {
		optionsTable.getColumns().get(0).setVisible(false);
		optionsTable.getColumns().get(0).setVisible(true);
	}
	
	public void refreshResultColumn() {
		questionsTable.getColumns().get(0).setVisible(false);
		questionsTable.getColumns().get(0).setVisible(true);
	}
	
	public void submitSolution(ActionEvent event) {
		try
		{
			ObservableList <CheckableQuestion> submitItems = questionsTable.getItems();
	        
			for(int counter = 0; counter < submitItems.size(); counter++)
			{
				if(submitItems.listIterator(counter).next().getAnswer().equals(submitItems.listIterator(counter).next().getGuess()))
				{
					submitItems.listIterator(counter).next().setChecked(true);
				}
			}
			
			refreshResultColumn();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void resetTest(ActionEvent event) {
		try
		{
			ObservableList<CheckableQuestion> resetItems = questionsTable.getItems();
			
			for(int counter = 0; counter < resetItems.size(); counter++)
			{
				resetItems.listIterator(counter).next().setGuess("empty");
				resetItems.listIterator(counter).next().setChecked(false);
			}
			
			refreshResultColumn();
		}
		catch (Exception e)
		{
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
	
	public void initialize(int tid) {
		System.out.println(tid);
		MySQLDAO dao;
		try
		{
			dao = new MySQLDAO();
			QuestionsList qList = dao.loadQuestionsByTest(tid);
			//qList.size()
			data = FXCollections.observableList(qList.convertToCheckableQuestions());
			optionsData = FXCollections.observableList(qList.convertToCheckableQuestions());
			
			body.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, String>("body"));
			body.setCellFactory(TextFieldTableCell.<CheckableQuestion>forTableColumn());
			
			result.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, Boolean>("checked"));
			result.setCellFactory(CheckBoxTableCell.forTableColumn(result));

			answer.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, String>("guess"));
			answer.setCellFactory(TextFieldTableCell.<CheckableQuestion>forTableColumn());
			
			questionsTable.setItems(data);
			
			FXCollections.shuffle(optionsData);
			
			options.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, String>("answer"));
			options.setCellFactory(TextFieldTableCell.<CheckableQuestion>forTableColumn());
			
			optionsTable.setItems(optionsData);
			
			
			//initializeListeners();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
}
