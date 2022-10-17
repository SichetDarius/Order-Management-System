package com.example.pt2022_30421_sichet_darius_assignment_3.Controller;
import com.example.pt2022_30421_sichet_darius_assignment_3.HelloApplication;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import static com.example.pt2022_30421_sichet_darius_assignment_3.utils.ControllerUtils.renderView;
public class LoginController {
    public void manageClient(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "client.fxml");
    }

    public void manageProduct(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "Product.fxml");
    }

    public void manageOrder(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "Order.fxml");
    }
}
