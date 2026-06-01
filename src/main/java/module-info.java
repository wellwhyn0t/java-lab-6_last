module com.student.lab6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.student.lab6 to javafx.fxml;
    exports com.student.lab6;
}