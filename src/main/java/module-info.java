module com.example.pt2022_30421_sichet_darius_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.java;


    opens com.example.pt2022_30421_sichet_darius_assignment_3 to javafx.fxml;
    exports com.example.pt2022_30421_sichet_darius_assignment_3;
    exports com.example.pt2022_30421_sichet_darius_assignment_3.Model;


    opens com.example.pt2022_30421_sichet_darius_assignment_3.Controller to javafx.fxml;
    exports com.example.pt2022_30421_sichet_darius_assignment_3.Controller;


}