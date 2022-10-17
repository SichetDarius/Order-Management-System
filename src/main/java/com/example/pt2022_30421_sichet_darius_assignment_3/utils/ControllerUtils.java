package com.example.pt2022_30421_sichet_darius_assignment_3.utils;


import com.example.pt2022_30421_sichet_darius_assignment_3.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerUtils {
    private static Stage stage;
    private static Scene scene;

    public static void renderView(Stage currentStage, String viewName) {
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(viewName));
            stage = currentStage;
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
