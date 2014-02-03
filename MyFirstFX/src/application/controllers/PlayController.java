package application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
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
	
	
	
	void initializeListeners(final TableView<CheckableQuestion> optionsTable)
	{
		optionsTable.setOnDragDetected(new EventHandler<MouseEvent>()
				{

					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						System.out.println("setOnDragDetected");
						
						Dragboard dragBoard = optionsTable.startDragAndDrop(TransferMode.MOVE);
						
						ClipboardContent content = new ClipboardContent();
						
						content.put(CheckableQuestion.CheckableQuestion_DATA_FORMAT, optionsTable.getSelectionModel().getSelectedItem());
						//content.putString(optionsTable.getSelectionModel().getSelectedItem().getAnswer());
						
						dragBoard.setContent(content);
					}
				});
		
		optionsTable.setOnDragDone(new EventHandler<DragEvent>()
				{

					@Override
					public void handle(DragEvent dragEvent) {
						// TODO Auto-generated method stub
						System.out.println("setOnDragDone");
					}
			
				});
		
		questionsTable.setOnDragEntered(new EventHandler<DragEvent>()
				{

					@Override
					public void handle(DragEvent dragEvent) {
						// TODO Auto-generated method stub
						System.out.println("setOnDragEntered");
						questionsTable.setBlendMode(BlendMode.DIFFERENCE);
					}
			
				});
		
		questionsTable.setOnDragExited(new EventHandler<DragEvent>()
				{

					@Override
					public void handle(DragEvent dragEvent) {
						// TODO Auto-generated method stub
						System.out.println("setOnDragExited");
						questionsTable.setBlendMode(null);
					}
			
				});
		
		questionsTable.setOnDragOver(new EventHandler<DragEvent>()
				{

					@Override
					public void handle(DragEvent dragEvent) {
						// TODO Auto-generated method stub
						System.out.println("setOnDragOver");
						dragEvent.acceptTransferModes(TransferMode.MOVE);
					}
			
				});
		
		questionsTable.setOnDragDropped(new EventHandler<DragEvent>()
				{

					@Override
					public void handle(DragEvent dragEvent) {
						// TODO Auto-generated method stub
						System.out.println("setOnDragDropped");
						CheckableQuestion retrieveAnswer = (CheckableQuestion) dragEvent.getDragboard().getContent(CheckableQuestion.CheckableQuestion_DATA_FORMAT);
						questionsTable.getItems().addAll(retrieveAnswer);
						dragEvent.setDropCompleted(true);
					}
			
				});
	}

	public void initialize(int tid) {
		System.out.println(tid);
		
		MySQLDAO dao;
		try
		{
			dao = new MySQLDAO();
			QuestionsList qList = dao.loadQuestionsByTest(tid);
			data = FXCollections.observableList(qList.convertToCheckableQuestions());
			
			body.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, String>("body"));
			body.setCellFactory(TextFieldTableCell.<CheckableQuestion>forTableColumn());
			
			result.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, Boolean>("checked"));
			result.setCellFactory(CheckBoxTableCell.forTableColumn(result));
			
			questionsTable.setItems(data);
			
			options.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, String>("answer"));
			options.setCellFactory(TextFieldTableCell.<CheckableQuestion>forTableColumn());
			
			optionsTable.setItems(data);
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
}
