package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import application.dao.MySQLDAO;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			/* test pane
			BorderPane root = new BorderPane();
			Text t = new Text("Hello FX"); //set text here
			root.setCenter(t);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Ooohh, CSS
			primaryStage.setScene(scene);
			primaryStage.show();*/
			
			//load home page
			// AnchorPane homePage = (AnchorPane) FXMLLoader.load(Main.class.getResource("darkHome.fxml"));
			// Scene scene = new Scene(homePage);
			// primaryStage.setScene(scene);
			// primaryStage.setTitle("Home Page");
			// primaryStage.show();
			
			//my comment
			//load new scene
			// AnchorPane playPage = (AnchorPane) FXMLLoader.load(Main.class.getResource("darkPlay.fxml"));
			// scene = new Scene(playPage);
			// primaryStage.setScene(scene);
			primaryStage.setTitle("Withmatching");
			// primaryStage.show();
			
			primaryStage.setScene(
		            createScene(
		                loadMainPane()
		            )
		        );
		
		        primaryStage.show();
			
			//try out the db connectivity
			System.out.println("Loading ДБ...");
			
			MySQLDAO dao = new MySQLDAO();
			dao.testDataBase(); //should print out the first question
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	* Loads the main fxml layout.
	* Sets up the vista switching VistaNavigator.
	* Loads the first vista into the fxml layout.
	*
	* @return the loaded pane.
	* @throws IOException if the pane could not be loaded.
	*/
	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		
		Pane mainPane = (Pane) loader.load(
		    getClass().getResourceAsStream(
		        VistaNavigator.MAIN
		    )
		);
		
		MainController mainController = loader.getController();
		
		VistaNavigator.setMainController(mainController);
		VistaNavigator.loadVista(VistaNavigator.HOME);
		
		return mainPane;
	}

	/**
	* Creates the main application scene.
	*
	* @param mainPane the main application layout.
	*
	* @return the created scene.
	*/
	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(
		    mainPane
		);
		
		scene.getStylesheets().setAll(
		    getClass().getResource("application.css").toExternalForm()
		);
		
		return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
