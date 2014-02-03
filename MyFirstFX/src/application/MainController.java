package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import application.controllers.PlayController;
import application.controllers.TestsController;

/**
 * Main controller class for the entire layout.
 */
public class MainController {

    /** Holder of a switchable vista. */
    @FXML
    private StackPane vistaHolder;

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node, FXMLLoader loader) {
        vistaHolder.getChildren().setAll(node);
        if (loader.getController() instanceof PlayController) {
        	((PlayController) loader.getController()).initialize(TestsController.selectedTestId);
        } else {
        	((Initializable) loader.getController()).initialize(null, null);
        }
        
        
    }

}
