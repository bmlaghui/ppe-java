package menuGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainUserGui extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Inscriptions Sportives");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
