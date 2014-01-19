package application;
	
import application.dao.MySQLDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
			AnchorPane homePage = (AnchorPane) FXMLLoader.load(Main.class.getResource("darkHome.fxml"));
			Scene scene = new Scene(homePage);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Home Page");
			primaryStage.show();
			
			//my comment
			//load new scene
			AnchorPane playPage = (AnchorPane) FXMLLoader.load(Main.class.getResource("darkPlay.fxml"));
			scene = new Scene(playPage);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Play Test");
			primaryStage.show();
			
			//try out the db connectivity
			System.out.println("Loading ДБ...");
			
			MySQLDAO dao = new MySQLDAO();
			dao.testDataBase(); //should print out the first question
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
