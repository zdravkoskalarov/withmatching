package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import application.dao.MySQLDAO;
import application.dto.CheckableQuestion;
import application.dto.Question;
import application.dto.QuestionsList;
import application.dto.Test;
import application.dto.TestsList;


public class BindingController implements Initializable {
	@FXML
	AnchorPane binding;
	
	@FXML
	Label label;
	
	@FXML
	Button saveBtn;
	
	@FXML
	ListView<Test> testsList;
	@FXML
	ObservableList<Test> testsData;
	
	@FXML
	TableView<CheckableQuestion> questionsTable;
	
	@FXML
	TableColumn<CheckableQuestion, Boolean> bound;
	@FXML
	TableColumn<CheckableQuestion, String> body;
	@FXML
	TableColumn<CheckableQuestion, String> answer;
	
	ObservableList<CheckableQuestion> data;
	
	//Current Test for editing
	Test currentTest;
	
	@FXML
	public void changedCheckbox(CellEditEvent<CheckableQuestion, Boolean> t) {
                System.out.println(t.getNewValue());
    }
	
	@FXML
	void saveQuestonsForTest(ActionEvent event) {
		System.out.println("Saving Questions!");
		if (currentTest != null) {
			System.out.println("We have a selected test!");
			try {
				currentTest.setQuestions(new QuestionsList());
				
				for (CheckableQuestion cq: questionsTable.getItems()) {
					System.out.println(questionsTable.getColumns().get(0).getCellData(0));
					if (cq.isChecked().get()) {
						currentTest.getQuestions().add((Question) cq);
						System.out.println("Found Checked!");
					}
				}
				
				
				MySQLDAO dao = new MySQLDAO();
				dao.updateTest(currentTest);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    		
    }
	
	public void resetAllQuestions() {
		for (CheckableQuestion q: questionsTable.getItems()) {
			q.setChecked(false);
		}
		
		refreshCheckBoxes();
	}
	
	public void refreshCheckBoxes() {
		questionsTable.getColumns().get(0).setVisible(false);
		questionsTable.getColumns().get(0).setVisible(true);
	}
	
	public void checkQuestions(QuestionsList qList) {
		if (qList != null) for (CheckableQuestion cq: questionsTable.getItems()) {
			for (Question q: qList) {
				if (cq.getId() == q.getId()) {
					cq.setChecked(true);
					break;
				}
			}
		}
		
		refreshCheckBoxes();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		MySQLDAO dao;
		try {
			
			
			dao = new MySQLDAO();
			//Load Questions
			QuestionsList qList = dao.loadAllQuestions();
			
			
			
			/*data =  FXCollections.<CheckableQuestion>observableArrayList( 
			        new Callback<CheckableQuestion, Observable[]>() {
			          @Override
			          public Observable[] call(CheckableQuestion cq) {
			        	  System.out.println("List changed");
			            return new Observable[]{cq.isChecked()};
			          }
			        }
			    );*/
			
			//data.addAll(qList.convertToCheckableQuestions());
			data = FXCollections.observableList(qList.convertToCheckableQuestions());
			
			/*data.addListener(new ListChangeListener<CheckableQuestion>() {
				@Override
				public void onChanged(
						javafx.collections.ListChangeListener.Change<? extends CheckableQuestion> change) {
					// TODO Auto-generated method stub
					System.out.println("List changed");
			        while (change.next()) {
			          if (change.wasUpdated()) {
			            System.out.println("List updated");
			            System.out.println(change.getAddedSubList());
			          }
			        }
				}
			    });*/
			
			
			
			
			
			bound.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, Boolean>("checked"));
			
			bound.setCellFactory(CheckBoxTableCell.forTableColumn(bound));
			
			
			body.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, String>("body"));
			
			
			answer.setCellValueFactory(new PropertyValueFactory<CheckableQuestion, String>("answer"));
			
			
			
			
			questionsTable.setItems(data);
			
			//Add change listener
			/*questionsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckableQuestion>() {

				@Override
				public void changed(
						ObservableValue<? extends CheckableQuestion> arg0,
						CheckableQuestion arg1, CheckableQuestion arg2) {
					// TODO Auto-generated method stub
					 System.out.println("Clicked!");
					if(questionsTable.getSelectionModel().getSelectedItem() != null) {
	                    System.out.println("Changed!");
	                }
				}
	        });*/
			
			
			//Load Tests
			dao = new MySQLDAO();
			
			TestsList tList = dao.loadAllTests();
			testsData = FXCollections.observableList(tList);
			
			testsList.setItems(testsData);
			
			//add event listener on list item selection
			testsList.getSelectionModel().selectedItemProperty().addListener(
		            new ChangeListener<Test>() {
						@Override
						public void changed(
								ObservableValue<? extends Test> arg0,
								Test oldTest, Test newTest) {
							label.setText("Editing '"+newTest+"'");
							currentTest = newTest;
							//reset all question checkboxes
							resetAllQuestions();
							//Check the questions related to the selected Test
							checkQuestions(newTest.getQuestions());
							
						}
		        });
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
}
